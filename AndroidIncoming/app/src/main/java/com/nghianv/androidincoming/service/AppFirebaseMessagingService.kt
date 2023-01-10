package com.nghianv.androidincoming.service

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AppFirebaseMessagingService: FirebaseMessagingService() {
	companion object{
		const val TYPE_INCOMING_CALL = "type_incoming_call"
	}

	override fun onNewToken(token: String) {
		super.onNewToken(token)
		Log.d("TAG", "Fcm token is: ${token}")
	}

	override fun onMessageReceived(message: RemoteMessage) {
		super.onMessageReceived(message)
		if (message.data["type"] == TYPE_INCOMING_CALL) {
			showInComingCallPopup()
		}
	}

	private fun showInComingCallPopup(){
		val intent = Intent(this, CallingService::class.java)
		startService(intent)
	}
}