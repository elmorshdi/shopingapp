package com.elmorshdi.trainingtask.datasource.network

import android.content.SharedPreferences
import android.util.Log
import com.elmorshdi.trainingtask.view.util.SharedPreferencesManager.getToken
import okhttp3.Interceptor
import javax.inject.Inject

class MyInterceptor
@Inject constructor(private val sharedPreferences: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
       val token= getToken(sharedPreferences)
        Log.d("token","interceptor :$token")
        return if (token.isNotEmpty()) {
            Log.d("token","interceptor :$token")
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization","Bearer $token")
                .addHeader("Accept", "application/json")
                .build()
            print("token null")
            chain.proceed(request)
        } else {
            println(token)
            val request = chain.request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(request)
        }
    }
}