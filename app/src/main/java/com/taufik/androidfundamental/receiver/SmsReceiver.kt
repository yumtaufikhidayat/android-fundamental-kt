package com.taufik.androidfundamental.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.taufik.androidfundamental.activity.SmsReceiverActivity

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObj = bundle.get("pdus") as Array<*>
                pdusObj.forEach { aPdusObj ->
                    val currentMessage = getIncomingMessage(aPdusObj as Any, bundle)
                    val senderNum = currentMessage.displayOriginatingAddress
                    val message = currentMessage.displayMessageBody
                    Log.i(TAG, "senderNum: $senderNum; message: $message")

                    val showSmsIntent = Intent(context, SmsReceiverActivity::class.java)
                    showSmsIntent.apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        putExtra(SmsReceiverActivity.EXTRA_SMS_NO, senderNum)
                        putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, message)
                    }
                    context.startActivity(showSmsIntent)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception smsReceiver: $e", )
        }
    }

    private fun getIncomingMessage(aObject: Any, bundle: Bundle): SmsMessage {
        val currentSms: SmsMessage
        val format = bundle.getString("format")
        currentSms = if (Build.VERSION.SDK_INT >= 23) {
            SmsMessage.createFromPdu(aObject as ByteArray, format)
        } else {
            SmsMessage.createFromPdu(aObject as ByteArray)
        }

        return currentSms
    }

    companion object {
        private val TAG = SmsReceiver::class.java.simpleName
    }
}