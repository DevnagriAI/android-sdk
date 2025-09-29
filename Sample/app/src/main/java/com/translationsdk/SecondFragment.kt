package com.translationsdk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.devnagritranslationsdk.DevnagriTranslationSDK
import com.translationsdk.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

private var _binding: FragmentSecondBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      _binding = FragmentSecondBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DevnagriTranslationSDK.getTranslationOfString("issue Background Verification 1", callback = { translation ->
          //  Log.d("check_translation_issue", "Translation: $translation")
        })

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        /*val aa = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            mutableListOf<String>()  // Start with an empty mutable list
        )*/
        val aa = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.best_cricket_eleven_women,
            android.R.layout.simple_list_item_1
        )
        aa.context

        binding.listView.adapter = aa
    }
override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}