package com.app.calllogs

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.app.calllogs.MyVari.phoneNumber
import com.app.calllogs.MyVari.timeEnd
import com.app.calllogs.MyVari.timeStart
import com.app.calllogs.MyVari.type
import com.app.calllogs.database.AllContactEntity
import com.app.calllogs.database.AppDatabase
import com.app.calllogs.database.CallLogEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CallReceiver : BroadcastReceiver() {
    private var lastState: Int = TelephonyManager.CALL_STATE_IDLE
    var isComingCall = false;
    var isCallPicked = false;
    var timestamp = System.currentTimeMillis()


    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)



        when (state) {
            TelephonyManager.EXTRA_STATE_RINGING -> {
                timeStart = System.currentTimeMillis()
//                if (number != null)
//                    saveCall(context, number, "INCOMING", timestamp)
                isComingCall = true;
                isCallPicked = true
//                lastState = TelephonyManager.EXTRA_STATE_RINGING.toInt();
            }

            TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                if (timeStart == 0L)
                    timeStart = System.currentTimeMillis()
                isCallPicked = true
                if (isComingCall) {
                    Log.d("CallStatus", "Incoming call answered/active.");
                    if (number != null) {
                        type = "INCOMING"
                        phoneNumber = number
                        sendNotify(context)
                    }
                } else {
                    Log.d("CallStatus", "Outgoing call is active.");
                    // Handle active incoming call logic
                    if (number != null) {
                        phoneNumber = number
                        type = "OUTGOING"
                        sendNotify(context)
                    }
                }
            }

            TelephonyManager.EXTRA_STATE_IDLE -> {

            }
        }
    }

    private fun sendNotify(context: Context) {
        timeEnd = System.currentTimeMillis()
        GlobalScope.launch {
            val contact = getAllContacts(context, phoneNumber = phoneNumber)
            if (contact != null) {
                saveCall(context, contact._cid, phoneNumber, type, timestamp, timeStart, timeEnd)
                sendMyBroadcast(
                    context,
                    contact._cid,
                    contact.type,
                    "Status: " + contact.type+" "+contact.name + " " + phoneNumber,
                    phoneNumber
                )

            } else {
                saveCall(context, "", phoneNumber, type, timestamp, timeStart, timeEnd)
                sendMyBroadcast(
                    context,
                    "",
                    "",
                    "Status: not exist! "+getContactNameFromPhoneNumber(context, phoneNumber ?: "")
                        ?: "unknown" + " " + phoneNumber,
                    phoneNumber
                )
            }
            isCallPicked = false
            phoneNumber = ""
            type = ""
            timeStart = 0L
            timeEnd = 0L
        }

    }

    private suspend fun getAllContacts(context: Context, phoneNumber: String): AllContactEntity? {
        val db = AppDatabase.getDatabase(context)
        val dao = db.contactsDao()
        return dao.getContact(phoneNumber)
    }

    private suspend fun saveCall(
        context: Context,
        cid: String,
        number: String?,
        type: String,
        time: Long,
        startTime: Long,
        endTime: Long
    ) {
        val db = AppDatabase.getDatabase(context)
        val dao = db.callLogDao()
        dao.insert(
            CallLogEntity(
                0,
                _cid = cid,
                number ?: "Unknown",
                name = getContactNameFromPhoneNumber(context, number ?: "") ?: "unknown",
                type = type,
                start_time = startTime,
                end_time = endTime,
                timestamp = time,
                note = "Call answered/active."
            )
        )
    }

    suspend fun getContactNameFromPhoneNumber(
        context: Context,
        phoneNumber: String
    ): String? = withContext(Dispatchers.IO) {

        // 1) Permission check (MOST COMMON CRASH)
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

        if (!granted) {
            Log.w("ContactLookup", "READ_CONTACTS not granted")
            return@withContext null
        }

        // 2) Input guard
        val normalized = phoneNumber.trim()
        if (normalized.isEmpty()) return@withContext null

        val uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(normalized)
        )

        val projection = arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME)

        return@withContext try {
            context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val idx =
                        cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME)
                    cursor.getString(idx).also {
                        Log.d("ContactLookup", "Contact Found: $it")
                    }
                } else null
            }
        } catch (se: SecurityException) {
            Log.e("ContactLookup", "No permission: ${se.message}", se)
            null
        } catch (iae: IllegalArgumentException) {
            Log.e("ContactLookup", "Bad URI/arg: ${iae.message}", iae)
            null
        } catch (e: Exception) {
            Log.e("ContactLookup", "Lookup failed: ${e.message}", e)
            null
        }
    }

    fun sendMyBroadcast(context: Context,id : String, type: String, message: String, number : String) {
        val intent = Intent(context, MyNotificationReceiver::class.java).apply {
            // You can add extra data to the intent if needed
//            putExtra("message_key", "Hello from the sender!")
            putExtra("id", id)
            putExtra("title", type)
            putExtra("message", message)
            putExtra("number", number)
        }
        context.sendBroadcast(intent)
    }
}
