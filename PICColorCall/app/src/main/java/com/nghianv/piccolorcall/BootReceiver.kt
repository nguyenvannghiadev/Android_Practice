package com.nghianv.piccolorcall

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver: BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		if (context == null || intent == null) return
		if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
			val serviceIntent = Intent(context, MainApplication::class.java)
			context.startService(serviceIntent)
		}
	}
}