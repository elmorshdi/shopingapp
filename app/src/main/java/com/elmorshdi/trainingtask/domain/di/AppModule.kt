package com.elmorshdi.trainingtask.domain.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.BaseApplication
import com.elmorshdi.trainingtask.Constant
import com.elmorshdi.trainingtask.Constant.APP_SETTINGS
import com.elmorshdi.trainingtask.Constant.TOKENN
import com.elmorshdi.trainingtask.R
import com.elmorshdi.trainingtask.datasource.network.ApiService
import com.elmorshdi.trainingtask.datasource.network.MyInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
    }
    @Singleton
    @Provides
    fun provideToken(sharedPref: SharedPreferences) = sharedPref.getString(TOKENN, "") ?: ""


        }


