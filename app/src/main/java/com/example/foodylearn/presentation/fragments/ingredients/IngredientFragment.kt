package com.example.foodylearn.presentation.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodylearn.adapters.IngredientsAdapter
import com.example.foodylearn.databinding.FragmentIngredientBinding
import com.example.foodylearn.models.Result
import com.example.foodylearn.util.Constants

class IngredientFragment : Fragment() {

    private var _binding: FragmentIngredientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        val adapter =  IngredientsAdapter()
        binding.rvIngredients.adapter = adapter
        binding.rvIngredients.layoutManager = LinearLayoutManager(requireContext())
        myBundle?.let { result ->
            result.extendedIngredients?.let {
                adapter.setData(it)
            }
        }

        return view
    }

}