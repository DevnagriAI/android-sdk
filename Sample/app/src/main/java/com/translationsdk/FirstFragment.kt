package com.translationsdk

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.devnagritranslationsdk.DevnagriTranslationSDK
import com.translationsdk.databinding.FragmentFirstBinding
import java.util.Locale

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        var userSelectedIndex = 0
    }


    val listOfStrings: ArrayList<String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textviewSecond.setOnClickListener {
            /*val intent = Intent(requireContext(), testActivity::class.java)
            startActivity(intent)*/

            Log.d("test_public_methods", "Start Testing Public Methods")
            DevnagriTranslationSDK.getTranslationOfString(
                "Bottle",
                callback = { translation ->
                    Log.d("test_public_methods", "Translated String: $translation")
                })


            val hashMap : HashMap<String, String> = HashMap()
            hashMap["Name"] = "Garima Nepalia"
            hashMap["Designation"] = "Quality Engineer"
            DevnagriTranslationSDK.getTranslationOfMap(hashMap, callback = { map ->
                Log.d("test_public_methods", "Translated Map: $map")
            })


            val strings : ArrayList<String?> = ArrayList()
            strings.add("Rajasthan, India")
            strings.add("Tamil")
            strings.add("Chatisgadh")
            DevnagriTranslationSDK.getTranslationOfStrings(strings, callback = { translation: ArrayList<String?> ->
                Log.d("test_public_methods", "Translated List: $strings")
            })
        }

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.buttonSecond.setOnClickListener {

            val supportableLanguages: HashMap<String, String> =
                DevnagriTranslationSDK.getAllSupportableLanguages()
            val listOfKeys = supportableLanguages.keys.toTypedArray()

            var locale = Locale("hi")
            if (!supportableLanguages.isNullOrEmpty()) {
                locale = Locale(supportableLanguages[listOfKeys[userSelectedIndex]])
            }

            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.change_app_language))
                .setPositiveButton(getString(R.string.change)) { dialog, _ ->
                    dialog.dismiss()
                    DevnagriTranslationSDK.updateAppLocale(
                        activity as MainActivity,
                        locale,
                        completionHandler = { isCompleted, error ->
                            if(error != null) {
                                Log.d("Error", error.toString())
                            }
                            Log.d("isCompleted", isCompleted.toString())
//                            Log.d("update_all_locale", "app locale updated","error", error.toString(), "isCompleted", isCompleted)
                        })
                }
                .setSingleChoiceItems(
                    listOfKeys,
                    userSelectedIndex
                ) { _, i ->
                    userSelectedIndex = i
                    locale = Locale(supportableLanguages[listOfKeys[i]])
                }
                .setCancelable(true)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}