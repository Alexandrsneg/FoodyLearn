package com.example.foodylearn.presentation.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodylearn.viewmodels.MainViewModel
import com.example.foodylearn.R
import com.example.foodylearn.adapters.RecipesAdapter
import com.example.foodylearn.databinding.FragmentRecipesBinding
import com.example.foodylearn.util.NetworkListener
import com.example.foodylearn.util.NetworkResult
import com.example.foodylearn.util.observeOnce
import com.example.foodylearn.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

//    private val args by navArgs<RecipesFragmentArgs>() нельзя чистить или менять
    private var args: Boolean = false

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter() }

    private var searchQuery: String? = null

    private lateinit var networkListener: NetworkListener


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

        args = arguments?.getBoolean("backFromBottomSheet") ?: false

        setHasOptionsMenu(true)

        setUpRecyclerView()
        lifecycleScope.launch {
            recipesViewModel.readBackOnline.collect {
                recipesViewModel.backOnline = it
            }
        }

        networkListener = NetworkListener()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Log.d("NetworkListener", status.toString())
                    recipesViewModel.isNetworkStatusAvailable = status
                    recipesViewModel.showNetworkStatus()
                    readDatabase()
                }
        }


        binding.recipesFab.setOnClickListener {
            if (recipesViewModel.isNetworkStatusAvailable)
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            else
                recipesViewModel.showNetworkStatus()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
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
        binding.shimmerRecyclerView.adapter = mAdapter
        binding.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) {
                if (it.isNotEmpty() && !args) {
                    mAdapter.setData(it[0].foodRecipes)
                    showShimmerEffect(false)
                } else {
                    requestApiData(false)
                    arguments?.clear()
                }
            }
        }
    }

    private fun requestApiData(isSearch:Boolean = false) {
        mainViewModel.getRecipes(recipesViewModel.applyQueries(searchQuery), isSearch)
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