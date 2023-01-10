package com.nghianv.piccolorcall

import android.content.Context
import android.telecom.TelecomManager
import android.telephony.TelephonyManager

class Call {

	companion object {
		private var instance: Call? = null

		@JvmStatic
		fun getInstance(): Call {
			if (instance == null) {
				instance = Call()
			}
			return instance!!
		}
	}

	fun answer(context: Context) {
		try {
			val telecomManager =
				(context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager)
			telecomManager.javaClass.getMethod("acceptRingingCall").invoke(telecomManager)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	fun dismiss(context: Context) {
		try {
			val telephonyManager =
				(context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)
			telephonyManager.javaClass.getMethod("endCall").invoke(telephonyManager)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}