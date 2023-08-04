package com.example.demopdfview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pspdfkit.datastructures.TextSelection
import com.pspdfkit.ui.special_mode.manager.TextSelectionManager

class PdfActivity : AppCompatActivity(), TextSelectionManager.OnTextSelectionChangeListener {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_pdf)
	}

	override fun onBeforeTextSelectionChange(p0: TextSelection?, p1: TextSelection?): Boolean {
		TODO("Not yet implemented")
	}

	override fun onAfterTextSelectionChange(p0: TextSelection?, p1: TextSelection?) {
		TODO("Not yet implemented")
	}
}