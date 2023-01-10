package com.nghianv.piccolorcall

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
	val PERMISSIONS = arrayOf(
		Manifest.permission.ANSWER_PHONE_CALLS,
		Manifest.permission.CALL_PHONE,
		Manifest.permission.DISABLE_KEYGUARD,
		Manifest.permission.RECEIVE_BOOT_COMPLETED,
		Manifest.permission.READ_CONTACTS,
		Manifest.permission.READ_PHONE_STATE,
		Manifest.permission.READ_CALL_LOG,
		Manifest.permission.SYSTEM_ALERT_WINDOW
	)
	val PERMISSIONS_REQUEST_CODE = 1
	val OVERLAY_PERMISSION_REQUEST_CODE = 2


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		requestPermissions()
	}
	private fun requestPermissions() {
		for (permission in PERMISSIONS) {
			if (permission == Manifest.permission.SYSTEM_ALERT_WINDOW) {
				continue
			}
			if (ActivityCompat.checkSelfPermission(
					this,
					permission
				) === PackageManager.PERMISSION_DENIED
			) {
				ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSIONS_REQUEST_CODE)
				return
			}
		}
		if (!Settings.canDrawOverlays(this)) {
			val intent = Intent(
				Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse(
					"package:$packageName"
				)
			)
			startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE)
		}
	}

	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		when (requestCode) {
			PERMISSIONS_REQUEST_CODE, OVERLAY_PERMISSION_REQUEST_CODE -> requestPermissions()
		}
	}
}