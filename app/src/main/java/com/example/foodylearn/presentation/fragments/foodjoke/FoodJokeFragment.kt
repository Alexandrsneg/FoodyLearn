package com.example.foodylearn.presentation.fragments.foodjoke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.foodylearn.R
import com.example.foodylearn.util.NetworkResult
import com.example.foodylearn.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_food_joke.view.*

class FoodJokeFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_food_joke, container, false)

        mainViewModel.getJoke()
        mainViewModel.jokeResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    view.tvJoke.text = it.data?.text
                    view.pbCircleProgress.visibility = View.GONE
                }
                is NetworkResult.Error -> {
                    view.tvJoke.visibility = View.GONE
                    view.ivJokeNotFound.visibility = View.VISIBLE
                }
                is NetworkResult.Loading -> {
                    view.pbCircleProgress.visibility = View.VISIBLE
                }
            }
        }

        return view
    }



}