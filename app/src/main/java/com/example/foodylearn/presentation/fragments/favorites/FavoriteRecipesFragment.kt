package com.example.foodylearn.presentation.fragments.favorites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodylearn.R
import com.example.foodylearn.adapters.FavoriteRecipesAdapter
import com.example.foodylearn.databinding.FragmentFavoriteRecipesBinding
import com.example.foodylearn.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_recipes.view.*

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val adapter by lazy { FavoriteRecipesAdapter(requireActivity(), mainViewModel) }

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = adapter

        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())


        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorites_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.remove_all){
            mainViewModel.deleteAllFavorites()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.closeActionMenu()
        _binding = null
    }

}