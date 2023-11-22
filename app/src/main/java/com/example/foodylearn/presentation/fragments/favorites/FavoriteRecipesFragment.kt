package com.example.foodylearn.presentation.fragments.favorites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodylearn.R
import com.example.foodylearn.presentation.adapters.FavoriteRecipesAdapter
import com.example.foodylearn.databinding.FragmentFavoriteRecipesBinding
import com.example.foodylearn.databinding.FragmentFoodJokeBinding
import com.example.foodylearn.presentation.fragments.ABaseFragment
import com.example.foodylearn.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment :
    ABaseFragment<FragmentFavoriteRecipesBinding>(FragmentFavoriteRecipesBinding::inflate) {

    private val mainViewModel: MainViewModel by viewModels()
    private val adapter by lazy { FavoriteRecipesAdapter(requireActivity(), mainViewModel) }

    //todo need to refactor
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = adapter

        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())


        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorites_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == R.id.remove_all) {
            mainViewModel.deleteAllFavorites()
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    override fun onDestroy() {
        adapter.closeActionMenu()
        super.onDestroy()
    }

}