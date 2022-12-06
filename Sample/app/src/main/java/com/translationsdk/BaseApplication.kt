package com.translationsdk

import android.app.Application
import android.util.Log
import com.devnagritranslationsdk.DevNagriTranslationSdk


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val strings=R.string::class.java.fields
        val arrays=R.array::class.java.fields
        val API_KEY="<Enter Your API Key Here>"
        Log.d("DevNagriSDK_TAG", "Start init process")
        DevNagriTranslationSdk.init(applicationContext,API_KEY,10,strings,arrays, null, initListener = {isInitialized, error ->
            Log.d("DevNagriSDK_TAG", "Complete init process")
        } )
    }
}
