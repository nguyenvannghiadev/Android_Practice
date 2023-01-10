package com.nghianv.piccolorcall

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
	var Apply: Button? = null
	var MAKE_YOUR_THEME: TextView? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		Apply = findViewById<Button>(R.id.GoToCSD)
		MAKE_YOUR_THEME = findViewById<TextView>(R.id.make_theme)

	}
}