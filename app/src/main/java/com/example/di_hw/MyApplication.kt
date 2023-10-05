package com.example.di_hw

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class MyApplication: Application() {
}