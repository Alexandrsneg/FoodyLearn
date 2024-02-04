package com.example.foodylearn.presentation.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

import androidx.navigation.navArgs
import com.example.foodylearn.R
import com.example.foodylearn.presentation.adapters.PagerAdapter
import com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity
import com.example.foodylearn.databinding.ActivityDetailsBinding
import com.example.foodylearn.presentation.fragments.ingredients.IngredientFragment
import com.example.foodylearn.presentation.fragments.instructions.InstructionsFragment
import com.example.foodylearn.presentation.fragments.overview.OverViewFragment
import com.example.foodylearn.presentation.viewmodels.MainViewModel
import com.example.foodylearn.util.Constants.RECIPE_RESULT_KEY
import com.example.foodylearn.util.extensions.stringByRes
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : ABaseActivity<ActivityDetailsBinding>(ActivityDetailsBinding::inflate) {

    companion object {
        private const val VP_ADAPTER = "VP_ADAPTER"
    }

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var isFavorite = false
    private var favoriteMenuItem: MenuItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViewPager()
        initObservers()
    }

    private fun initObservers() {
//        mainViewModel.readFavoriteRecipes.observe(this) {
//            renderFavoriteIcon(it)
//        }
    }

    private fun initViewPager() {
        val fragments = ArrayList<Fragment>().apply {
            add(OverViewFragment())
            add(IngredientFragment())
            add(InstructionsFragment())
        }

        val adapter = PagerAdapter(
            bundleOf(RECIPE_RESULT_KEY to args.recipe),
            fragments,
            this
        )

        binding.viewPager.adapter = adapter

        val titles = ArrayList<String>().apply {
            add(stringByRes(R.string.overview))
            add(stringByRes(R.string.ingredients))
            add(stringByRes(R.string.instructions))
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        favoriteMenuItem = menu.findItem(R.id.menu_favorite)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_favorite -> if (isFavorite) removeFavorite() else saveFavorite()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveFavorite() {
        //todo intent
//        mainViewModel.insertFavorites(FavoritesEntity(args.recipe.id!!, args.recipe))
    }

    private fun removeFavorite() {
        //todo intent
//        mainViewModel.deleteFavoriteById(args.recipe.id!!)
    }

    private fun renderFavoriteIcon(favoritesEntities: List<FavoritesEntity>) {
        favoriteMenuItem?.let { menuItem ->
            favoritesEntities.firstOrNull { it.recipe.id == args.recipe.id }?.let { favEntity ->
                isFavorite = true
                changeItemColor(menuItem, R.color.yellow)
            } ?: run {
                isFavorite = false
                changeItemColor(menuItem, R.color.white)
            }
        }
    }

    private fun changeItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }
}