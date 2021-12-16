package com.example.foodylearn.presentation.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.foodylearn.R
import com.example.foodylearn.models.Result
import com.example.foodylearn.util.Constants
import kotlinx.android.synthetic.main.fragment_instructions.view.*

class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_instructions, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        view.wvInstructions.webViewClient = object : WebViewClient() {

        }
        myBundle?.spoonacularSourceUrl?.let {
            view.wvInstructions.loadUrl(it)
        }

        return view;
    }

}