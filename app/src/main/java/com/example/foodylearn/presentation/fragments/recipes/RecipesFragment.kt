package com.example.foodylearn.presentation.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodylearn.presentation.viewmodels.MainViewModel
import com.example.foodylearn.R
import com.example.foodylearn.presentation.adapters.RecipesAdapter
import com.example.foodylearn.databinding.FragmentRecipesBinding
import com.example.foodylearn.util.observeOnce
import com.example.foodylearn.presentation.viewmodels.RecipesViewModel
import com.example.foodylearn.util.repeatOnLifecycleExt
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    //    private val args by navArgs<RecipesFragmentArgs>() нельзя чистить или менять
    private fun backFromBottomSheet() = arguments?.getBoolean("backFromBottomSheet") ?: false

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()
    private val recipesViewModel by viewModels<RecipesViewModel>()
    private val mAdapter by lazy { RecipesAdapter() }

    private var searchQuery: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        binding.recipesFab.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
        }

        readDatabase()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        with(menu.findItem(R.id.menu_search).actionView as? SearchView) {
            this ?: return
            isSubmitButtonEnabled = true
            setOnQueryTextListener(this@RecipesFragment)
        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        searchQuery = p0
        requestApiData(true)
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }


    private fun setUpRecyclerView() {
        with(binding.shimmerRecyclerView) {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) {
                if (it.isNotEmpty() && !backFromBottomSheet()) {
                    mAdapter.setData(it[0].foodRecipes)
                    showShimmerEffect(false)
                } else {
                    requestApiData(false)
                    arguments?.clear()
                }
            }
        }
    }

    private fun requestApiData(isSearch: Boolean = false) {
        mainViewModel.getRecipes(recipesViewModel.applyQueries(searchQuery), isSearch)
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is com.example.domain.models.NetworkResult.Success -> {
                    showShimmerEffect(false)
                    result.data?.let { mAdapter.setData(it) }
                }

                is com.example.domain.models.NetworkResult.Error -> {
                    showShimmerEffect(false)
                    loadDataFromCache()
                    Toast.makeText(requireContext(), result.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                is com.example.domain.models.NetworkResult.Loading -> {
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