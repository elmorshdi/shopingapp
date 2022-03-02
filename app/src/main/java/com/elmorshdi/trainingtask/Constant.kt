package com.elmorshdi.trainingtask

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import java.util.regex.Pattern


object Constant {
    const val BASE_URL = "https://android-training.appssquare.com/api/"
      const val APP_SETTINGS = "APP_SETTINGS"

    // properties
      var TOKENN = "TOKEN_VALUE"

      const val USER_NAME = "NAME_VALUE"
      const val LOGGED_IN = "LOGGED_IN"

}