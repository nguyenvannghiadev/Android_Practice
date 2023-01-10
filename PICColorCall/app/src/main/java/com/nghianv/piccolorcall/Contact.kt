package com.nghianv.piccolorcall

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import android.util.Log

class Contact constructor(phoneNumber: String) {
	private var context: Context? = null

	enum class Number {
		HIDDEN, JUST_PHONE, FULL
	}

	var type = Number.HIDDEN

	var number: String? = null
	var name: String? = null
	var company: String? = null
	var companyPosition: String? = null
	var photo: Bitmap? = null

	init {
		try {
			context = MainApplication.instance
			setNumberContact(phoneNumber)
			type = Number.JUST_PHONE
			setContact(phoneNumber)
			type = Number.FULL
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}


	private fun setNumberContact(phoneNumber: String) {
		var numberInt: Long = -1
		try {
			numberInt = phoneNumber.toLong()
		} catch (ignored: Exception) {
		}
		if (numberInt < 0) {
			Log.d("appInfo", "Hidden number")
		}
		number = PhoneNumberUtils.formatNumber(phoneNumber, "US")
		if (number == null) {
			number = phoneNumber
		}
	}

	private fun setContact(phoneNumber: String) {
		val contentResolver = context!!.contentResolver


		// Получаем URI номера
		val phoneUri = Uri.withAppendedPath(
			ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
			Uri.encode(phoneNumber)
		)

		// Получаем контакт по номеру
		val contactCursor = contentResolver.query(phoneUri, null, null, null, null)!!
		contactCursor.moveToFirst()
		val countID = contactCursor.getColumnIndex(ContactsContract.Contacts._ID)
		val countDisplay = contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
		var contactId = ""


		if (contactCursor != null && contactCursor.count >= 0 && countID >= 0) {

			contactId =
				contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID))
		}
		if (contactCursor != null && contactCursor.count >= 0 && countDisplay >= 0) {
			name =
				contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
		}
		contactCursor.close()


		// Получаем организацию по ID контакта
		val orgCursor = contentResolver.query(
			ContactsContract.Data.CONTENT_URI,
			null,
			ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?",
			arrayOf(
				contactId,
				ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
			),
			null
		)!!
		if (orgCursor.moveToFirst()) {
			company =
				orgCursor.getString(orgCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY))
			companyPosition =
				orgCursor.getString(orgCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE))
		}
		orgCursor.close()

		val contactUri =
			ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId.toLong())

		val displayPhotoUri =
			Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO)
		photo = try {
			val photoFd = contentResolver.openAssetFileDescriptor(displayPhotoUri, "r")!!
			BitmapFactory.decodeStream(photoFd.createInputStream())
		} catch (e: Exception) {
			null
		}
	}
}