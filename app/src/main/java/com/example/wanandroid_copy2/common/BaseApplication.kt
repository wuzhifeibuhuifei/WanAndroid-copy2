package com.example.wanandroid_copy2.common

import android.app.Application
import com.example.wanandroid_copy2.common.utils.Preference

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Preference.setContext(applicationContext)
    }
}