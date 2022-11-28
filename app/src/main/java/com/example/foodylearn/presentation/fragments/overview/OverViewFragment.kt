package com.example.foodylearn.presentation.fragments.overview

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.foodylearn.databinding.FragmentOverViewBinding
import com.example.foodylearn.models.Result


class OverViewFragment : Fragment() {

    private var _binding: FragmentOverViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverViewBinding.inflate(inflater, container, false)

        (arguments?.getParcelable("recipeBundle") as? Result)?.let {
            renderFragment(it)
        }
        return binding.root
    }

    private fun renderFragment(result: Result) {
        with(binding) {
            ivMainImage.load(result.image)
            tvTitle.text = result.title
            tvFavorites.text = result.aggregateLikes.toString()
            tvMinutes.text = result.readyInMinutes.toString()
            tvTitle.text = result.title
            tvSummary.text = Html.fromHtml(result.summary)

            if (result.vegetarian)
                cvVegetarian.active = true

            if (result.vegan)
                cvVegan.active = true

            if (result.glutenFree == true)
                cvGlutenFree.active = true

            if (result.dairyFree == true)
                cvDairyFree.active = true

            if (result.veryHealthy)
                cvHealthy.active = true

            if (result.cheap == true)
                cvCheap.active = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}