package com.translationsdk

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.devnagritranslationsdk.DevNagriTranslationSDK
import com.translationsdk.databinding.FragmentFirstBinding
import java.util.*
import kotlin.collections.HashMap

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object{
        var userSelectedIndex=0
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      _binding = FragmentFirstBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.buttonSecond.setOnClickListener {

            val supportableLanguages:HashMap<String,String> = DevNagriTranslationSDK.getAllSupportableLanguages()
            val listOfKeys=supportableLanguages.keys.toTypedArray()


            var locale = Locale("en")
            if (!supportableLanguages.isNullOrEmpty()) {
                locale= Locale(supportableLanguages[listOfKeys[userSelectedIndex]])
            }

            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.change_app_language))
                .setPositiveButton(getString(R.string.change)) { dialog, _ ->
                    dialog.dismiss()
                    Log.d("DevNagriSDK_TAG", "Start update App Locale")
                    DevNagriTranslationSDK.updateAppLocale(activity as MainActivity,locale, completionHandler = {isCompleted, error ->
                        Log.d("DevNagriSDK_TAG", "Complete update App Locale")
                    })
                }
                .setSingleChoiceItems(
                    listOfKeys,
                    userSelectedIndex
                ) { _, i ->
                    userSelectedIndex=i
                    locale= Locale(supportableLanguages[listOfKeys[i]])
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