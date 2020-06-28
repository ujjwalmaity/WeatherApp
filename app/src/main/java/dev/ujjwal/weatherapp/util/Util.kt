package dev.ujjwal.weatherapp.util

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast


fun Context.storeUserDetails(key: String, msg: String) {
    val sharedPreferences: SharedPreferences = this.getSharedPreferences(PREF_USER_DETAILS_FILE, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(key, msg)
    editor.apply()
}

fun Context.getUserDetails(key: String): String {
    val sharedPreferences = this.getSharedPreferences(PREF_USER_DETAILS_FILE, Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, PREF_DEFAULT_VALUE)!!
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
