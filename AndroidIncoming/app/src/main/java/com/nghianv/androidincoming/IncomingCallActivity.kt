package com.nghianv.androidincoming

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nghianv.androidincoming.databinding.ActivityIncomingCallBinding
import com.nghianv.androidincoming.service.CallingService
import kotlinx.android.synthetic.main.activity_main.*


class IncomingCallActivity : AppCompatActivity() {
	private lateinit var databinding: ActivityIncomingCallBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		databinding = DataBindingUtil.setContentView(this, R.layout.activity_incoming_call)
		setContentView(R.layout.activity_incoming_call)
		registerViewsEvent()
	}

	private fun registerViewsEvent() {
		btnRejectCall.setOnClickListener {
			val callingService = Intent(this, CallingService::class.java)
			stopService(callingService)
		}
		btnAcceptCall.setOnClickListener {
			// TODO start calling screen
		}
	}
}