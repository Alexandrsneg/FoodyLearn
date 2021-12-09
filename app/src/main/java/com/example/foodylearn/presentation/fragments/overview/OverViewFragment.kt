package com.example.foodylearn.presentation.fragments.overview

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.foodylearn.R
import com.example.foodylearn.models.Result
import kotlinx.android.synthetic.main.fragment_over_view.view.*


class OverViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_over_view, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")

        myBundle?.let{
            with(view){
                ivMainImage.load(it.image)
                tvTitle.text = it.title
                tvFavorites.text = it.aggregateLikes.toString()
                tvMinutes.text = it.readyInMinutes.toString()
                tvTitle.text = it.title
                tvSummary.text =  Html.fromHtml(it.summary)

                if (it.vegetarian)
                    cvVegetarian.active = true

                if (it.vegan)
                    cvVegan.active = true

                if (it.glutenFree == true)
                    cvGlutenFree.active = true

                if (it.dairyFree == true)
                    cvDairyFree.active = true

                if (it.veryHealthy)
                    cvHealthy.active = true

                if (it.cheap == true)
                    cvCheap.active = true
            }
        }


        return view
    }
}