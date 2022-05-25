package com.example.kiosko_model

import android.app.Application
import com.onesignal.OneSignal

const val ONESIGNAL_APP_ID = "7de86026-515f-40cd-ac57-fd4208067de0"

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

//        // Logging set to help debug issues, remove before releasing your app.
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
//
//        // OneSignal Initialization
//        OneSignal.initWithContext(this)
//        OneSignal.setAppId(ONESIGNAL_APP_ID)
////        OneSignal.

    }
}