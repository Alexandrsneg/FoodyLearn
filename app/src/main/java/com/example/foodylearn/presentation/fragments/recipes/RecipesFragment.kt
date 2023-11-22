package com.example.foodylearn.presentation.fragments.recipes

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodylearn.presentation.viewmodels.MainViewModel
import com.example.foodylearn.R
import com.example.foodylearn.presentation.adapters.RecipesAdapter
import com.example.foodylearn.databinding.FragmentRecipesBinding
import com.example.foodylearn.presentation.activities.ABaseActivity
import com.example.foodylearn.presentation.activities.MainActivity
import com.example.foodylearn.presentation.fragments.ABaseFragment
import com.example.foodylearn.presentation.mvi.MainScreenUserIntent
import com.example.foodylearn.presentation.viewmodels.RecipesViewModel
import com.example.foodylearn.util.UiText
import com.example.foodylearn.util.extensions.fadeVisibilityAnimate
import com.example.foodylearn.util.repeatOnLifecycleExt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : ABaseFragment<FragmentRecipesBinding>(FragmentRecipesBinding::inflate),
    SearchView.OnQueryTextListener {

    //    private val args by navArgs<RecipesFragmentArgs>() нельзя чистить или менять
    private fun backFromBottomSheet() = arguments?.getBoolean("backFromBottomSheet") ?: false


    private val mainViewModel by viewModels<MainViewModel>()
    private val recipesViewModel by viewModels<RecipesViewModel>()
    private val recipesAdapter by lazy { RecipesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recipesFab.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
        }
        setUpRecyclerView()
        initScreenStateObserver()

        if (savedInstanceState == null)
            mainViewModel.onMainScreenIntent(
                MainScreenUserIntent.OnGetRecipes(recipesViewModel.applyQueries())
            )
    }

    private fun initScreenStateObserver() {
        repeatOnLifecycleExt(Lifecycle.State.STARTED) {
            mainViewModel.recipesFragmentState.collect {
                showShimmerEffect(it.isLoading)
                renderError(it.error)
                recipesAdapter.setData(it.recipes)
            }
        }
    }

    private fun renderError(error: UiText?) {
        binding.llErrorContainer.fadeVisibilityAnimate(error != null)
        error?.let {
            binding.tvError.text = it.asString(requireContext())
        }
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
        // TODO: requestNewData
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }


    private fun setUpRecyclerView() {
        with(binding.shimmerRecyclerView) {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }


    private fun showShimmerEffect(show: Boolean) {
        with(binding.shimmerRecyclerView) {
            if (show) showShimmer() else hideShimmer()
        }
    }
}