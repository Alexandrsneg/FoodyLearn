package com.example.foodylearn.presentation.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodylearn.presentation.adapters.IngredientsAdapter
import com.example.foodylearn.databinding.FragmentIngredientBinding
import com.example.foodylearn.models.Result
import com.example.foodylearn.util.Constants

class IngredientFragment : Fragment() {

    private var _binding: FragmentIngredientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientBinding.inflate(inflater, container, false)
        val myBundle: Result? = arguments?.getParcelable(Constants.RECIPE_RESULT_KEY)

        val _adapter = IngredientsAdapter()
        with(binding.rvIngredients) {
            adapter = _adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        myBundle?.let { result ->
            result.extendedIngredients?.let {
                _adapter.setData(it)
            }
        }
        return binding.root
    }

}