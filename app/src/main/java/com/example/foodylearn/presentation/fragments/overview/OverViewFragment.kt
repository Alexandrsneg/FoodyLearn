package com.example.foodylearn.presentation.fragments.overview

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.foodylearn.databinding.FragmentOverViewBinding
import com.example.foodylearn.data.models.Recipe
import com.example.foodylearn.util.Constants.RECIPE_RESULT_KEY


class OverViewFragment : Fragment() {

    private var _binding: FragmentOverViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverViewBinding.inflate(inflater, container, false)

        (arguments?.getParcelable(RECIPE_RESULT_KEY) as? Recipe)?.let {
            renderFragment(it)
        }
        return binding.root
    }

    private fun renderFragment(recipe: Recipe) {
        with(binding) {
            ivMainImage.load(recipe.image)
            tvTitle.text = recipe.title
            tvFavorites.text = recipe.aggregateLikes.toString()
            tvMinutes.text = recipe.readyInMinutes.toString()
            tvTitle.text = recipe.title
            tvSummary.text = Html.fromHtml(recipe.summary)

            if (recipe.vegetarian)
                cvVegetarian.active = true

            if (recipe.vegan)
                cvVegan.active = true

            if (recipe.glutenFree == true)
                cvGlutenFree.active = true

            if (recipe.dairyFree == true)
                cvDairyFree.active = true

            if (recipe.veryHealthy)
                cvHealthy.active = true

            if (recipe.cheap == true)
                cvCheap.active = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}