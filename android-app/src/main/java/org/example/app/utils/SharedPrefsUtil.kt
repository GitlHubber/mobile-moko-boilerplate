package org.example.app.utils

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import androidx.fragment.app.Fragment

const val SHARED_PREF_DB_NAME = "boilerplate_prefs"
const val USER_AUTH_ENTERED_KEY = "IS_ENTERED"

fun Activity.getUserAuthorizedState(key: String, defValue: Boolean): Boolean {
    val sharedPref = getSharedPreferences(SHARED_PREF_DB_NAME, MODE_PRIVATE)
    return sharedPref.getBoolean(key, defValue)
}

fun Fragment.setUserAuthorizedState(key: String, value: Boolean) {
    val sharedPrefEditor = requireActivity().getSharedPreferences(SHARED_PREF_DB_NAME, MODE_PRIVATE).edit()
    sharedPrefEditor.putBoolean(key, value)
    sharedPrefEditor.apply()
}