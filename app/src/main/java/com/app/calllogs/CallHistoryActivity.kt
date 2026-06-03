package com.app.calllogs

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.app.calllogs.database.AppDatabase
import com.app.calllogs.database.CallLogEntity
import com.app.calllogs.ui.home.HomeActivity
import com.app.calllogs.ui.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CallHistoryActivity : AppCompatActivity() {
    private lateinit var telephonyManager: TelephonyManager
    private lateinit var phoneStateListener: CustomPhoneStateListener

    private lateinit var rv: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var ivLogo: ImageView
    private lateinit var adapter: CallLogAdapter
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val dao by lazy { db.callLogDao() }

    lateinit var  listItems : List<CallLogEntity>
    private var openedEditOnce = false
    companion object {
        private const val PERMISSION_REQUEST_CODE = 101
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        phoneStateListener = CustomPhoneStateListener(this)


        rv = findViewById(R.id.rvCallLogs)
        tvEmpty = findViewById(R.id.tvEmpty)
        ivLogo = findViewById(R.id.ivLogo)

        adapter = CallLogAdapter { item ->
            showEditDialog(item)
        }
        rv.adapter = adapter

        val id = intent?.getStringExtra("cid")?:""

//        if (!shouldOpenEdit)
//            checkAndRequestPermissions()

        // Auto-update UI whenever DB changes
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dao.observeAllById(id).collect { list ->
                    adapter.submitList(list)
                    listItems = list
                    tvEmpty.visibility = if (list.isEmpty()) TextView.VISIBLE else TextView.GONE
                    ivLogo.visibility =  ImageView.GONE
//                    if (shouldOpenEdit && !openedEditOnce && list.isNotEmpty()) {
//                        openedEditOnce = true
//                        showEditDialog(list[0])
//                    }
                }
            }
        }

    }


    private fun checkAndRequestPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.PROCESS_OUTGOING_CALLS
        )
        val listPermissionsNeeded = ArrayList<String>()
        for (p in permissions) {
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), PERMISSION_REQUEST_CODE)
            return false
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startListeningToCallState()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Permissions denied. Cannot track call metadata.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
    private fun startListeningToCallState() {
        // Start listening to call state changes
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop listening when the activity is destroyed
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE)
    }

    private fun showEditDialog(item: CallLogEntity) {
        val fmt = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_call_log, null, false)
        val etNumber = view.findViewById<EditText>(R.id.etNumber)
        val etType = view.findViewById<EditText>(R.id.etType)
        val etNote = view.findViewById<EditText>(R.id.etNote)
        val etStartTime = view.findViewById<EditText>(R.id.etStartTime)
        val etEndTime = view.findViewById<EditText>(R.id.etEndTime)

        etNumber.setText(item.number)
        etType.setText(item.type)
        etStartTime.setText(fmt.format(Date(item.start_time)))
        etEndTime.setText(fmt.format(Date(item.end_time)))
        etNote.setText(item.note ?: "")

        MaterialAlertDialogBuilder(this)
            .setTitle("Edit Call Log")
            .setView(view)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save") { _, _ ->
                val newNumber = etNumber.text?.toString()?.trim().orEmpty()
                val newType = etType.text?.toString()?.trim()?.uppercase().orEmpty()
                val newNote = etNote.text?.toString()?.trim().orEmpty()


                val updated = item.copy(
                    number = if (newNumber.isEmpty()) item.number else newNumber,
                    type = if (newType.isEmpty()) item.type else newType,
                    note = newNote
                )

                lifecycleScope.launch {
                    dao.update(updated)
                }
            }
            .show()
    }
}