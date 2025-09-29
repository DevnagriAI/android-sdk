package com.translationsdk

import android.app.Application
import android.util.Log
import com.devnagritranslationsdk.DevnagriTranslationSDK


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val stringsField = R.string::class.java.fields
        val arraysField = R.array::class.java.fields
        val pluralField = null

        stringsField.forEach { field ->
            try {
                val id = field.getInt(field)
                val sentence: String = resources.getString(id) ?: ""
                if (sentence.equals("Amount paid", true)) {
                    Log.d("check_sentence", "onCreate: $sentence")
                }
            } catch (e: Exception) {
                Log.d(
                    "check_sentence",
                    "Resource Not found for key: " + field.getInt(field) + " name: " + field.name
                )
            }
        }

        val API_KEY = "Input your api key here"
        Log.d("devNagriTranslationTAG", "SDK initialising")
        DevnagriTranslationSDK.init(
            applicationContext,
            API_KEY,
            1,
            stringsField,
            arraysField,
            pluralField,
            initListener = { isInitialized, error ->
                if (isInitialized)
                {
                    Log.d("devNagriTranslationIntegrationTAG", "SDK initialised")

                }else{
                    Log.d("devNagriTranslationIntegrationTAG ", "devNagriTranslationTAG Error: "+ error);
                }
            })
    }
}