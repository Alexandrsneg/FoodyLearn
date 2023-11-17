package com.example.foodylearn.presentation.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.foodylearn.databinding.FragmentInstructionsBinding
import com.example.foodylearn.data.models.Recipe
import com.example.foodylearn.util.Constants

class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        val myBundle: Recipe? = arguments?.getParcelable(Constants.RECIPE_RESULT_KEY)

        binding.wvInstructions.webViewClient = object : WebViewClient() {}
        myBundle?.spoonacularSourceUrl?.let {
            binding.wvInstructions.loadUrl(it)
        }
        return binding.root;
    }

}