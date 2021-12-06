package com.example.foodylearn.presentation.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodylearn.viewmodels.MainViewModel
import com.example.foodylearn.R
import com.example.foodylearn.adapters.RecipesAdapter
import com.example.foodylearn.databinding.FragmentRecipesBinding
import com.example.foodylearn.util.NetworkResult
import com.example.foodylearn.util.observeOnce
import com.example.foodylearn.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter() }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel


        setUpRecyclerView()
        readDatabase()

        binding.recipesFab.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
        }

        return binding.root
    }

    private fun setUpRecyclerView() {
       binding.shimmerRecyclerView.adapter = mAdapter
       binding.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    mAdapter.setData(it[0].foodRecipes)
                    showShimmerEffect(false)
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    showShimmerEffect(false)
                    it.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    showShimmerEffect(false)
                    loadDataFromCache()
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect(true)
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    mAdapter.setData(it[0].foodRecipes)
                }
            }
        }
    }


    private fun showShimmerEffect(show: Boolean) {
        if (show)
            binding.shimmerRecyclerView.showShimmer()
        else
            binding.shimmerRecyclerView.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}