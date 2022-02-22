package com.elmorshdi.trainingtask.util

import android.content.Context
import android.content.SharedPreferences


object SharedPreferencesManager {
    private const val APP_SETTINGS = "APP_SETTINGS"

    // properties
    private var TOKEN = "TOKEN_VALUE"

    private const val USER_NAME = "NAME_VALUE"
    private const val LOGGED_IN = "LOGGED_IN"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
    }

    fun getUsername(context: Context): String? {
        return getSharedPreferences(context).getString(USER_NAME, null)
    }


    fun getLoginValue(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(LOGGED_IN, false)
    }


    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString(TOKEN, null)
    }

    /* fun setUsername(context: Context, newValue: String?) {
      val editor = getSharedPreferences(context).edit()
      editor.putString(USER_NAME, newValue)
      editor.apply()
       fun setToken(context: Context, newValue: String?) {
      val editor = getSharedPreferences(context).edit()
      editor.putString(TOKEN, newValue)
      editor.apply()
  }
          fun setLoginValue(context: Context, newValue: Boolean?) {
      val editor = getSharedPreferences(context).edit()

      editor.putBoolean(LOGGED_IN, newValue!!)

      editor.apply()
  }

  }*/


    fun signOutShared(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.clear().apply()
        editor.putString(TOKEN,null)
        editor.putBoolean(LOGGED_IN, false)
        editor.putString(USER_NAME, "")
        editor.apply()
    }

    fun signInShared(context: Context, token: String, userName: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(TOKEN, token)
        editor.putBoolean(LOGGED_IN, true)
        editor.putString(USER_NAME, userName)
        editor.apply()

    }
}