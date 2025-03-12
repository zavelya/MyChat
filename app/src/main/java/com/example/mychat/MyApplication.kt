package com.example.mychat

import android.app.Application
import com.onesignal.debug.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.onesignal.OneSignal

class MyApplication:Application() {
    companion object{
        lateinit var instance: MyApplication

        // NOTE: Replace the below with your own ONESIGNAL_APP_ID
        val ONESIGNAL_APP_ID = "66d4300c-6e57-4bd6-a4ec-67879c5ec35d"

    }
    override fun onCreate(){
        super.onCreate()
        instance=this
        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.Debug.logLevel = LogLevel.VERBOSE

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(false)
        }

    }

}