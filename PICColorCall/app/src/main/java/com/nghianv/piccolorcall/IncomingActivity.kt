package com.nghianv.piccolorcall

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class IncomingActivity : AppCompatActivity() {
	var phoneNumber: String = ""
	private var textView: TextView? = null

	override fun onNewIntent(intent: Intent?) {
		super.onNewIntent(intent)
		Log.d("appInfo",">>>>>>>>>>onNewIntent")
		beforeLoadActivity()
		processIntentData()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Log.d("appInfo",">>>>>>>>>>onCreate")
		//
		setContentView(R.layout.activity_incoming)
		textView = findViewById(R.id.tvPhoneNumber)
		beforeLoadActivity()
		//
		processIntentData()
	}
	private fun beforeLoadActivity() {
		val localIntent = intent
		if (localIntent != null && localIntent.data == null) {
			if (localIntent.extras == null) {
				Log.d("appInfo",">>>>>>>>>>>>>>localIntent.extras null")
				return
			}
			checkIntentData(localIntent)
		}
	}
	private fun checkIntentData(intent: Intent) {
		try {
			phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
			Log.d("appInfo",">>>checkIntentData_${phoneNumber}")

		} catch (e: Exception) {
			Log.e("appInfo","Error checkIntentData_${e.printStackTrace()}")
		}

	}
	private fun processIntentData() {
		if (textView == null || phoneNumber.isBlank()) {
			Log.d("appInfo",">>>checkIntentData_${phoneNumber}")
			return
		}
		textView?.setText(phoneNumber)

	}
}