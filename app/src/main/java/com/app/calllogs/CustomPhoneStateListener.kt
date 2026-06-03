package com.app.calllogs

import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast

class CustomPhoneStateListener(private val context: Context) : PhoneStateListener() {

    private val TAG = "CallMonitor"
    private var lastState = TelephonyManager.CALL_STATE_IDLE
    private var savedNumber: String? = null

    override fun onCallStateChanged(state: Int, phoneNumber: String?) {
        super.onCallStateChanged(state, phoneNumber)

        // phoneNumber is often only reliable in the RINGING state on older devices.
        // For ended calls, query the Call Log after transitioning from OFFHOOK to IDLE.

        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                Log.d(TAG, "CALL_STATE_RINGING: Incoming Number: $phoneNumber")
                Toast.makeText(context, "Incoming Call: $phoneNumber", Toast.LENGTH_SHORT).show()
                savedNumber = phoneNumber
            }
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                // Call is answered or outgoing
                Log.d(TAG, "CALL_STATE_OFFHOOK")
            }
            TelephonyManager.CALL_STATE_IDLE -> {
                // Call ends
                if (lastState == TelephonyManager.CALL_STATE_OFFHOOK) {
                    Log.d(TAG, "CALL_STATE_IDLE: Call ended. Number: $savedNumber")
                    // Query the call log for full details (duration, type, etc.)
                    // Requires READ_CALL_LOG permission
                    queryCallLog(context, savedNumber)
                }
            }
        }
        lastState = state
    }

    private fun queryCallLog(context: Context, number: String?) {
        // Implement call log query logic here using ContentResolver and CallLog.Calls.CONTENT_URI
        // This is a more involved process using Cursor.
        Log.d(TAG, "Querying call log for: $number")
    }
}
