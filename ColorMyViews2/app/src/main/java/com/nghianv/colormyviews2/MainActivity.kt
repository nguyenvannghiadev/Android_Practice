package com.nghianv.colormyviews2

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun makeColored(view: View) {
        when (view.id) {
            // Boxes using Color class colors for the background
            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)
            R.id.box_three_text -> view.setBackgroundColor(Color.BLUE)
            R.id.box_four_text -> view.setBackgroundColor(Color.MAGENTA)
            R.id.box_five_text -> view.setBackgroundColor(Color.BLUE)
            //
            R.id.button_red -> findViewById<TextView>(R.id.box_three_text).setBackgroundResource(R.color.my_red)
            R.id.button_yelllow -> findViewById<TextView>(R.id.button_yelllow).text = getString(R.string.app_name)
            R.id.button_green -> findViewById<TextView>(R.id.box_five_text).setBackgroundResource(R.color.my_green)
            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }

    private fun setListeners() {
        val boxOneText = findViewById<TextView>(R.id.box_one_text)
        val boxTwoText = findViewById<TextView>(R.id.box_two_text)
        val boxThreeText = findViewById<TextView>(R.id.box_three_text)
        val boxFourText = findViewById<TextView>(R.id.box_four_text)
        val boxFiveText = findViewById<TextView>(R.id.box_five_text)
        //
        val redButton = findViewById<TextView>(R.id.button_red)
        val greenButton = findViewById<TextView>(R.id.button_green)
        val yellowButton = findViewById<TextView>(R.id.button_yelllow)
//        val rootContraiLayout = findViewById<View>(R.id.constraint_layout)
        //
        val clickableViews: List<View> =
            listOf(boxOneText, boxTwoText, boxThreeText, boxFourText, boxFiveText, redButton, yellowButton, greenButton)
        //
        for (item in clickableViews) {
            item.setOnClickListener { makeColored(it) }
        }

    }

}