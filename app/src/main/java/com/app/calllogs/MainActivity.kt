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
import android.widget.LinearLayout
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

class MainActivity : AppCompatActivity() {
    private lateinit var telephonyManager: TelephonyManager
    private lateinit var phoneStateListener: CustomPhoneStateListener

    private lateinit var rv: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var ivLogo: ImageView
    private lateinit var adapter: CallLogAdapter
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val dao by lazy { db.callLogDao() }

    private val daoContact by lazy { db.contactsDao() }

    lateinit var listItems: List<CallLogEntity>
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
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        phoneStateListener = CustomPhoneStateListener(this)


        rv = findViewById(R.id.rvCallLogs)
        tvEmpty = findViewById(R.id.tvEmpty)
        ivLogo = findViewById(R.id.ivLogo)




        adapter = CallLogAdapter { item ->
            showEditDialog(item,false)
        }
        rv.adapter = adapter

        val shouldOpenEdit = intent?.getStringExtra("item") == "1"
        val phoneNumber = intent?.getStringExtra("number")
        val id = intent?.getStringExtra("id")?:""
        val title = intent?.getStringExtra("title")?:""


        if (!shouldOpenEdit)
            checkAndRequestPermissions()
        else {
            if (id.isNotEmpty() && title.isNotEmpty()){
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                intent.putExtra("item", "1")
                intent.putExtra("module", title.lowercase())
                intent.putExtra("id", id)
                startActivity(intent)
                finish()
            }

            // Auto-update UI whenever DB changes
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    dao.observeAll().collect { list ->
                        adapter.submitList(list)
                        listItems = list
                        tvEmpty.visibility = if (list.isEmpty()) TextView.VISIBLE else TextView.GONE
                        ivLogo.visibility =
                            if (list.isEmpty()) ImageView.VISIBLE else ImageView.GONE
                        if (shouldOpenEdit && !openedEditOnce && list.isNotEmpty()) {
                            val cnt = daoContact.getContact(phoneNumber ?: "")
                            openedEditOnce = true
                            showEditDialog(list[0], cnt != null)
                        }
                    }
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
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
            return false
        } else {
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
                Toast.makeText(
                    this,
                    "Permissions denied. Cannot track call metadata.",
                    Toast.LENGTH_LONG
                ).show()
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

    private fun showEditDialog(item: CallLogEntity, isContactExist: Boolean) {
        val fmt = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_call_log, null, false)
        val etNumber = view.findViewById<EditText>(R.id.etNumber)
        val etType = view.findViewById<EditText>(R.id.etType)
        val etNote = view.findViewById<EditText>(R.id.etNote)
        val etStartTime = view.findViewById<EditText>(R.id.etStartTime)
        val etEndTime = view.findViewById<EditText>(R.id.etEndTime)
        val lnDeal = view.findViewById<LinearLayout>(R.id.lnDeal)
        val lnLead = view.findViewById<LinearLayout>(R.id.lnLead)
        val lnContact = view.findViewById<LinearLayout>(R.id.lnContact)
        val lnMeeting = view.findViewById<LinearLayout>(R.id.lnMeeting)
        val lnTask = view.findViewById<LinearLayout>(R.id.lnTask)
        val lnCall = view.findViewById<LinearLayout>(R.id.lnCall)
        val intent = Intent(this@MainActivity, HomeActivity::class.java)
        intent.putExtra("item", "1")


        etNumber.setText(item.number)
        etType.setText(item.type)
        etStartTime.setText(fmt.format(Date(item.start_time)))
        etEndTime.setText(fmt.format(Date(item.end_time)))
        etNote.setText(item.note ?: "")
        lnDeal.setOnClickListener {
            intent.putExtra("module", "deal")
            startActivity(intent)
            finish()
        }
        lnContact.setOnClickListener {
            intent.putExtra("module", "contact")
            startActivity(intent)
            finish()
        }
        lnLead.setOnClickListener {
            intent.putExtra("module", "lead")
            startActivity(intent)
            finish()
        }
        lnTask.setOnClickListener {
            intent.putExtra("module", "task")
            startActivity(intent)
            finish()
        }
        lnMeeting.setOnClickListener {
            intent.putExtra("module", "meeting")
            startActivity(intent)
            finish()
        }
        lnCall.setOnClickListener {
            intent.putExtra("module", "call")
            startActivity(intent)
            finish()
        }

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