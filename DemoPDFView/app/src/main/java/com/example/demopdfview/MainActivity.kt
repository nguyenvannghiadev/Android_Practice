package com.example.demopdfview

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pspdfkit.configuration.activity.PdfActivityConfiguration
import com.pspdfkit.datastructures.TextSelection
import com.pspdfkit.ui.special_mode.manager.TextSelectionManager

class MainActivity : AppCompatActivity(), TextSelectionManager.OnTextSelectionChangeListener {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val uri = Uri.parse("file:///android_asset/100MauCaGiaoTiepTiengNhat.pdf")
		val config = PdfActivityConfiguration.Builder(this).build()
	}

	override fun onBeforeTextSelectionChange(p0: TextSelection?, p1: TextSelection?): Boolean {
		TODO("Not yet implemented")
	}

	override fun onAfterTextSelectionChange(p0: TextSelection?, p1: TextSelection?) {
		TODO("Not yet implemented")
	}
	
}