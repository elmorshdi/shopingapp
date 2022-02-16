package com.elmorshdi.trainingtask.network

import com.elmorshdi.trainingtask.util.Constant.BASE_URL
import com.elmorshdi.trainingtask.util.Constant.TOKEN
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstate {
    private val clint = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clint)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =chain.request()
            .newBuilder()
            .addHeader("Authorization",TOKEN)
            .build()

        return chain.proceed(request)
    }
}
}


