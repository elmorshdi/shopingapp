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

//  is string contain digit
/*fun CharSequence.containDigit(): Boolean {
    val length = this.length
    val digitMatcher = Pattern.compile("^(?=.*[0-9]).*$length,}$").matcher(this)
    return digitMatcher.matches()
}*/

// for signup to return dismissed
/*
fun CharSequence.isValidSignUpPassword(): String {
    val lowerCaseLetterMatcher = Pattern.compile("^(?=.*\\p{Ll}).{8,}$").matcher(this)
    val upperCaseLetterMatcher = Pattern.compile("^(?=.*\\p{Lu}).{8,}$").matcher(this)
    val specialCharacterMatcher = Pattern.compile("^(?=.*[!@#\$%^&+=_]).{8,}$").matcher(this)
    val digitMatcher = Pattern.compile("^(?=.*[0-9]).{8,}$").matcher(this)
//    (?=.{8,})(?=.*\p{Lu}.*\p{Lu})(?=.*[!@#$&*])(?=.*[0-9])(?=.*\p{Ll}.*\p{Ll})

    return when {
        this.isEmpty() -> "Must Not Be Empty"
        this.length <= 8 -> "Length Must Be More Than 8"
        this.contains(" ") -> "Must Not Have Space"
        !upperCaseLetterMatcher.matches() -> "Must  have upper case letter"
        !lowerCaseLetterMatcher.matches() -> "Must  have lower case letter"
        !specialCharacterMatcher.matches() -> "Must have special Character [!@#\$%^&+=_]"
        !digitMatcher.matches() -> "Must have number"
        else -> "true"
    }
}
*/

fun CharSequence.isValidPassword(): Boolean {
    val regex = "^(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*[!@#\$%^&+=_])(?=.*[0-9]).{8,}$"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()