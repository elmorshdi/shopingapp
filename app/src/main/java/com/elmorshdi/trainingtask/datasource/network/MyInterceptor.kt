package com.elmorshdi.trainingtask.datasource.network

import android.content.SharedPreferences
import com.elmorshdi.trainingtask.Constant
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Interceptor
import javax.inject.Inject
class MyInterceptor
    @Inject constructor(val token:String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
              if (token=="" ){
          val request =chain.request()
              .newBuilder()
//              .addHeader("Authorization","Bearer $TOKEN")
              .addHeader("Accept", "application/json")
              .build()
          print("token null")
          return chain.proceed(request)
      }
         else{
          val request =chain.request()
              .newBuilder()
              .addHeader("Authorization", token)
              .addHeader("Accept", "application/json")
              .build()
          return chain.proceed(request)
         }



    }
}