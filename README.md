# SDK Integration Steps:
# Introduction
Devnagri Over the Air for Android lets you update translations in your Android app without having to release it every single time.

By including our SDK, your app will check for updated translations in Devnagri regularly and download them in the background.

# Include the SDK
As a first step a new maven repository needs to be added to your default dependency resolution file set by your project, it can be either your project build.gradle file or settings.gradle file:

        repositories {

            ...

            maven { url 'https://jitpack.io' }

        }
	

Add the below dependency in your app build.gradle file:

	dependencies {
	
	    ...
	    
	    implementation ('com.github.DevnagriAI:android-sdk:2.0.2@aar') { transitive(true) }
	    
	}
        
       

 
# Compatibility
 Use Kotlin version 1.4.30 or above and Gradle JDK version 17. This SDK support only kotlin language. Target/Compile SDK should be 34.
 
# Configuration

Initialise the SDK in your application class and add the API_KEY from Devnagri. 

    class MainApplication : Application() {
    
      override fun onCreate() {
          super.onCreate()
            
          val syncTime:Int = 60  //In minutes, minimum 10 minute allowed
          
          val strings = R.string::class.java.fields
          val arrays = R.array::class.java.fields
          val plurals = R.plurals::class.java.fields
	  
	  // passing arrays and plurals in init method is optional here, pass them only if defined in strings.xml file
          DevnagriTranslationSDK.init(applicationContext, "API_KEY" ,syncTime, strings, arrays, plurals, initListener = {isInitialized, error ->
			
          })
      }
    }
 

Additionally, you need to inject the SDK in each activity, e.g. by creating a base activity which all other activities inherit from:

    class BaseActivity : AppCompatActivity() 
    {
        override fun getDelegate(): AppCompatDelegate 
        {
           return DevnagriTranslationSDK.fetchAppDelegate(this, super.getDelegate())
        }
    }

# Default Localisation Override
   The SDK override the functionality of @string and getString by default. 

# Change Language

In case you don't want to use the system language, you can set a different language in the updateAppLocale method. The language code (locale) needs to be present in a release from Devnagri.
updateAppLocale function may take some time (for example: 3-4 seconds), we suggest you to add a loader before calling updateAppLocale and stop in callback.

	//Start loader here
	val locale = Locale("hi");
	DevnagriTranslationSDK.updateAppLocale(this,locale, completionHandler = {isCompleted, error ->  
		//Stop loader here
	})

Please note that you will get the english text back if your device language is english or you have not set any specific language for the SDK. To get the translation in Hindi, Please update app locale to Hindi as per above method.

# Get Supported Languages

You can get supported languages for the SDK using this method.
This will return hashmap of language name and language code

	val supportedLangauges =  DevnagriTranslationSDK.getAllSupportableLanguages()
 

# Translate String, List and Map on runtime

You can use these methods anywhere in your project and these will provide translation for current active locale in callback method.

# Get Translation of a Specific String.

    DevnagriTranslationSDK.getTranslationOfString("SampleText"){ translation ->
  	   // use translated text here       
    }
 

# Get Translations of an Array of Strings.

    val arrayList = arrayListOf ("SampleText1","SampleText2","SampleText3")
    DevnagriTranslationSDK.getTranslationOfStrings(arrayList){ translations ->
  	   // use translated text here       
    }
 
 
# Get Translations Of HashMap 

    val map = hashMapOf (Pair("A","SampleText1"), Pair("B","SampleText2"), Pair("C","SampleText3") )
    DevnagriTranslationSDK.getTranslationOfMap(map){ translations ->
       // use translated map here
    }
 

# Get Translations Of JSON Object

   // This method is used to convert the entire JSON object to requested language
   // ignoreKeys -> Send a list of strings that you want to ignore during the conversion process

    val jsonObject = JSONObject()
    jsonObject.put("sample_key", "Sample Text")
    jsonObject.put("sample_key_one", "Sample Text1")

    val ignoreKeys: ArrayList<String> = ArrayList()
    ignoreKeys.add("_id")
    ignoreKeys.add("unit_id")

    DevnagriTranslationSDK.getTranslationOfJSON(jsonObject, ignoreKeys) {
	//use translated JSON Object here
    }

# For Modular Approach
   - Please add DevnagriSDK dependency in each module level gradle file.
   - If you have multiple modules in your application, 
   then in each module's base activity override the below method.

	override fun getDelegate(): AppCompatDelegate {
		return DevnagriTranslationSDK.fetchAppDelegate(this, super.getDelegate())
	}

# Usage

Translations can be used as usual in layouts:

    <TextView android:text="@string/translation_key" />


And inside code:

    val text = findViewById(R.id.text_id) as TextView
    text.setText(R.string.translation_key)

