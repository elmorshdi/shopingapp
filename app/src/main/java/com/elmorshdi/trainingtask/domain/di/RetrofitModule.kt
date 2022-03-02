package com.elmorshdi.trainingtask.domain.di

import com.elmorshdi.trainingtask.Constant
import com.elmorshdi.trainingtask.datasource.network.ApiService
import com.elmorshdi.trainingtask.datasource.network.MyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideInterceptor(string: String): MyInterceptor {
        return MyInterceptor(string)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(token:String): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            addInterceptor(MyInterceptor(token))
        }

        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit( client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    @Provides
    @Singleton
    fun provideApiService( retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}