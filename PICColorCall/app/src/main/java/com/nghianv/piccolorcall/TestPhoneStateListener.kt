package com.nghianv.piccolorcall

import android.content.Context
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager

class TestPhoneStateListener(private val context: Context) {
	private val telephonyManager: TelephonyManager =
		context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

	fun listener() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			telephonyManager.registerTelephonyCallback(
				context.mainExecutor,
				object : TelephonyCallback(), TelephonyCallback.CallStateListener {
					override fun onCallStateChanged(state: Int) {
						when (state) {
							TelephonyManager.CALL_STATE_RINGING -> {}
							else -> {}
						}
					}
				})
		} else {
			telephonyManager.listen(object : PhoneStateListener() {
				override fun onCallStateChanged(state: Int, phoneNumber: String?) {
					when (state) {
						TelephonyManager.CALL_STATE_RINGING -> {}
						else -> {}
					}
				}
			}, PhoneStateListener.LISTEN_CALL_STATE)
		}
	}


}

