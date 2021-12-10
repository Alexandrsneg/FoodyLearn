package com.example.foodylearn.presentation.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodylearn.R
import com.example.foodylearn.adapters.IngredientsAdapter
import com.example.foodylearn.models.Result
import kotlinx.android.synthetic.main.fragment_ingredient.view.*

class IngredientFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_ingredient, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")

        val adapter =  IngredientsAdapter()
        view.rvIngredients.adapter = adapter
        view.rvIngredients.layoutManager = LinearLayoutManager(requireContext())
        myBundle?.let {
            adapter.setData(it.extendedIngredients)
        }

        return view
    }

}