package com.translationsdk

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.devnagritranslationsdk.DevnagriTranslationSDK
import com.google.android.material.snackbar.Snackbar
import com.translationsdk.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun getDelegate(): AppCompatDelegate {
        return DevnagriTranslationSDK.fetchAppDelegate(this, super.getDelegate())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.fab.visibility = View.VISIBLE
        binding.fab.setOnClickListener { view ->

            DevnagriTranslationSDK.apply {
                /*updateTranslations()*/
                getTranslationOfString("Use your action"){
                    Snackbar.make(view, it, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }

                val testArray : ArrayList<String?> = ArrayList()
                testArray.add("test")
                testArray.add("book")
                testArray.add("pen")
                testArray.add("")
                getTranslationOfStrings(testArray, callback = {
                    Log.d("translation_tag_Array", "getTranslationOfStrings onCreate: $it")
                })

                val hash : HashMap<String, String> = HashMap()
                hash["test"] = "test_sentence"
                hash[""] = "empty_key_sentence"
                hash["empty_sentence_key"] = ""
                getTranslationOfMap(hash, callback = {
                    Log.d("translation_tag_HashMap", "translation sdk: $it")
                })

                val jsonObject = JSONObject()
                jsonObject.put("name", "dakshita mathur")
                jsonObject.put("age", 20)

                val jsonArray = JSONArray()
                jsonArray.put("Yash")
                jsonArray.put("Rishabh")
                jsonArray.put("Himanshu")

                jsonObject.put("team_mates", jsonArray)
                jsonObject.put("age", 20)
                val ignoreList: ArrayList<String> = ArrayList()
                ignoreList.add("name")


                getTranslationOfJSON(getJson(), ArrayList(), jsonListener = {jsonObjectTranslated ->
                    Log.d("translation_tag_Json", "getTranslationOfJson result: $jsonObjectTranslated")
                })

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun getJson(): JSONObject? {
        try {
            val `is` = resources.openRawResource(R.raw.test_json)
            val writer: Writer = StringWriter()
            val buffer = CharArray(1024)
            try {
                val reader: Reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
                var n: Int
                while (reader.read(buffer).also { n = it } != -1) {
                    writer.write(buffer, 0, n)
                }
            } finally {
                `is`.close()
            }
            val jsonString = writer.toString()
            return JSONObject(jsonString)
        } catch (exception: Exception) {
            Log.d("getJson_excep", "getJson exception: $exception")
        }
        return JSONObject()
    }
}