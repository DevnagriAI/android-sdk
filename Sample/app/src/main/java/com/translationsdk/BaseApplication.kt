package com.translationsdk

import android.app.Application
import com.devnagritranslationsdk.DevNagriTranslationSdk


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val strings=R.string::class.java.fields.map { it.name }
        val arrays=R.array::class.java.fields.map { it.name }


        val API_KEY="devnagri_9b3a4902cd4111ecbb6002bf838402f8"
        DevNagriTranslationSdk.init(applicationContext,API_KEY,strings,arrays)


    }

}
