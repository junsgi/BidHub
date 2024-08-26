package com.example.bidhubandroid

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class UserSharedPreferences : Application() {

    companion object {
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        // 공통 SharedPreferences 초기화
        sharedPreferences = applicationContext.getSharedPreferences("user", Context.MODE_PRIVATE)

    }

}