package com.fitscorp.sl.apps.di

import android.content.SharedPreferences
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.fitscorp.sl.apps.App
import com.fitscorp.sl.apps.rest.ApiService

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import javax.inject.Singleton

@Module
class MainModule(private val app: App, private val baseUrl: String) {

    @Singleton
    @Provides
    fun provideNetwork(): ApiService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(
                        GsonBuilder()
                                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                                .create())
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(
                        OkHttpClient.Builder()
                                .addNetworkInterceptor(StethoInterceptor())
                                .addInterceptor(interceptor).build())
                .build()
                .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSharePref(): SharedPreferences {
        val PREF_NAME = "fitscorpindikabookapp"
        val PRIVATE_MODE = 0
        return app.applicationContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    }

}