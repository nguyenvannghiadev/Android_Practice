package com.nghianv.piccolorcall

import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.telephony.TelephonyManager
import android.util.Log

class PhoneStateReceiver: BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		if (context == null || intent == null) return
		Log.d("appInfo", ">>>>>>>>>>>intent_${intent}")
		if (TelephonyManager.ACTION_PHONE_STATE_CHANGED == intent.action) {
			try {
				val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
				if (TelephonyManager.EXTRA_STATE_RINGING == state) {
					Log.d("appInfo", ">>>>>>>>>>>EXTRA_STATE_RINGING")
					processRinging(context, intent)
				}
				if (TelephonyManager.EXTRA_STATE_OFFHOOK == state) {
				}
				if (TelephonyManager.EXTRA_STATE_IDLE == state) {
				}
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}
	private fun processRinging(context: Context, intent: Intent) {
		val powerManager = (context.getSystemService(Context.POWER_SERVICE) as PowerManager)
		val keyguardManager =
			(context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager)
		if (!powerManager.isInteractive || keyguardManager.inKeyguardRestrictedInputMode()) {
		}
		val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
		Log.d("appInfo", "phoneNumber_${phoneNumber}")

//		var contact = Contact(context.getString(R.string.incoming_call))
//		contact = if (phoneNumber != null) Contact(phoneNumber) else contact
//		val ringingWindow: RingingWindow = RingingWindow.getInstance()
//		ringingWindow.setData(contact)
//		ringingWindow.show()
        if(phoneNumber?.isEmpty() == true) return
		val mIntent = Intent(context, IncomingActivity::class.java).apply {
			putExtra("phoneNumber", phoneNumber)
			addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
		}
		Log.d("appInfo",">>>>>>mIntent_${mIntent.getStringExtra("phoneNumber")}")
		context.startActivity(mIntent)
	}


}