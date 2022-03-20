package com.elmorshdi.trainingtask.helper

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.regex.Pattern

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun CharSequence.isEmailValid(): Boolean {
    return if (this.isNotEmpty()) {
        this.contains("@") && this.endsWith(".com")
    } else
        false
}

fun CharSequence.isValidPassword(): Boolean {
    val regex = "^(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*[!@#\$%^&+=_])(?=.*[0-9]).{8,}$"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()