package com.nghianv.androidincoming

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.nghianv.androidincoming.service.CallingService

class HangUpBroadcast: BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		context?.let { context ->
			val intent = Intent(context, CallingService::class.java)
			context.stopService(intent)
		}
	}
}