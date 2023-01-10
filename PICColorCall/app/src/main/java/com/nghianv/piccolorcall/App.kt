package com.nghianv.piccolorcall

import android.app.Application

class MainApplication : Application() {
	override fun onCreate() {
		super.onCreate()
		_instance = this
	}

	companion object {
		private lateinit var _instance: MainApplication
		val instance: MainApplication
			get() = _instance

	}

}