package com.example.foodylearn.presentation.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodylearn.R
import com.example.foodylearn.models.Result

class IngredientFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_ingredient, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")



        return view
    }

}