package com.nghianv.piccolorcall

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView

class RingingWindow {
	private var context: Context? = null
	private var ringingView: View? = null
	private var isShown = false

	init {
		context = MainApplication.instance
		val inflater =
			(context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
		ringingView = inflater.inflate(R.layout.activity_ringing, null)
		val answerButton: AppCompatImageView =
			ringingView?.findViewById(R.id.answer) as AppCompatImageView
		answerButton.setOnClickListener { answer() }
		val dismissButton: AppCompatImageView =
			ringingView?.findViewById(R.id.dismiss) as AppCompatImageView
		dismissButton.setOnClickListener { dismiss() }
	}

	companion object {
		private var instance: RingingWindow? = null
		@JvmStatic
		fun getInstance(): RingingWindow {
			if (instance == null) {
				instance = RingingWindow()
			}
			return instance!!
		}
	}



	fun setData(contact: Contact) {
		val resources = context!!.resources
		val name = ringingView!!.findViewById<TextView>(R.id.name)
		val phone = ringingView!!.findViewById<TextView>(R.id.phone)
		val company = ringingView!!.findViewById<TextView>(R.id.company)
		val photo = ringingView!!.findViewById<ImageView>(R.id.photo)
		name.visibility = View.GONE
		phone.visibility = View.GONE
		company.visibility = View.GONE
		photo.visibility = View.GONE
		when (contact.type) {
			Contact.Number.HIDDEN -> {
				name.setText(R.string.hidden_number)
				name.visibility = View.VISIBLE
			}
			Contact.Number.JUST_PHONE -> {
				name.text = contact.number
				name.visibility = View.VISIBLE
			}
			Contact.Number.FULL -> {
				name.text = contact.name
				name.visibility = View.VISIBLE
				phone.text = contact.number
				phone.visibility = View.VISIBLE
				if (contact.company != null) {
					if (contact.companyPosition != null) {
						company.text =
							resources.getString(
								R.string.full_company,
								contact.company,
								contact.companyPosition
							)
						company.visibility = View.VISIBLE
					} else {
						company.text = contact.company
						company.visibility = View.VISIBLE
					}
				}
				if (contact.photo != null) {
					photo.setImageBitmap(contact.photo)
					photo.visibility = View.VISIBLE
				}
			}
		}
	}

	fun show() {
		if (!isShown) {
			val params = WindowManager.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
				WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
				PixelFormat.TRANSLUCENT
			)
			params.gravity = Gravity.TOP
			val windowManager =
				(context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
			windowManager.addView(ringingView, params)
			isShown = true
		}
	}

	fun hide() {
		if (isShown) {
			val windowManager =
				(context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
			windowManager.removeView(ringingView)
			isShown = false
		}
	}


	private fun answer() {
		hide()
		val call: Call = Call.getInstance()
		call.answer(MainApplication.instance)
	}

	private fun dismiss() {
		hide()
		val call: Call = Call.getInstance()
		call.dismiss(MainApplication.instance)
	}
}