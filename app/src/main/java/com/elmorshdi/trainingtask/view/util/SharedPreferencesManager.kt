package com.elmorshdi.trainingtask.view.util

import android.content.SharedPreferences
import com.elmorshdi.trainingtask.Constant.LOGGED_IN
import com.elmorshdi.trainingtask.Constant.TOKEN
import com.elmorshdi.trainingtask.Constant.USER_NAME


object SharedPreferencesManager {

    fun getUsername(sharedPreferences: SharedPreferences): String? {
        return sharedPreferences.getString(USER_NAME, null)
    }
    fun getToken(sharedPreferences: SharedPreferences): String {
        return sharedPreferences.getString(TOKEN, "")?:""
    }
    fun getLoginValue(sharedPreferences: SharedPreferences): Boolean {
        return sharedPreferences.getBoolean(LOGGED_IN, false)
    }

    fun signOutShared(editor: SharedPreferences.Editor)  {
        editor.remove(TOKEN)
        editor.putBoolean(LOGGED_IN, false)
        editor.putString(USER_NAME, null)
         editor.apply()
    }

    fun signInShared(editor: SharedPreferences.Editor, token: String?, userName: String?) {
        editor.putString(TOKEN, token)
        editor.putBoolean(LOGGED_IN, true)
        editor.putString(USER_NAME, userName)
         editor.apply()

    }
}