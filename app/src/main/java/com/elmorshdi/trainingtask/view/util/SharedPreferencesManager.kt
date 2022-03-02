package com.elmorshdi.trainingtask.view.util

import android.content.Context
import android.content.SharedPreferences


object SharedPreferencesManager {
    private const val APP_SETTINGS = "APP_SETTINGS"

    // properties
    private var TOKEN = "TOKEN_VALUE"

    private const val USER_NAME = "NAME_VALUE"
    private const val LOGGED_IN = "LOGGED_IN"


    fun getUsername(sharedPreferences: SharedPreferences): String? {
        return sharedPreferences.getString(USER_NAME, null)
    }


    fun getLoginValue(sharedPreferences: SharedPreferences): Boolean {
        return sharedPreferences.getBoolean(LOGGED_IN, false)
    }



    fun signOutShared(sharedPreferences: SharedPreferences) {
        val editor = sharedPreferences.edit()
        editor.clear().apply()
        editor.putString(TOKEN,null)
        editor.putBoolean(LOGGED_IN, false)
        editor.putString(USER_NAME, "")
        editor.apply()
    }

    fun signInShared(sharedPreferences: SharedPreferences, token: String, userName: String) {
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN, "Bearer $token")
        editor.putBoolean(LOGGED_IN, true)
        editor.putString(USER_NAME, userName)
        editor.apply()

    }
}