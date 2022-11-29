package com.example.foodylearn.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var isFavorite = false

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverViewFragment())
        fragments.add(IngredientFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.menu_favorite)
        checkSavesRecipe(menuItem)
        return true
    }

    private fun checkSavesRecipe(menuItem: MenuItem?) {
        mainViewModel.readFavoriteRecipes.observe(this) {
            try {
                it.firstOrNull { it.recipes.id == args.result.id }?.let {
                    isFavorite = true
                    changeItemColor(menuItem!!, R.color.yellow)
                } ?: run {
                    isFavorite = false
                    changeItemColor(menuItem!!, R.color.white)
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        if (item.itemId == R.id.menu_favorite) {
            if (isFavorite)
                removeFavorite(item)
            else
                saveFavorite(item)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveFavorite(item: MenuItem) {
        val favoritesEntity =
            com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity(
                args.result.id ?: 0, args.result
            )
        mainViewModel.insertFavorites(favoritesEntity)

        changeItemColor(item, R.color.yellow)
        showSnackBar("Recipe saved")
    }

    private fun removeFavorite(item: MenuItem) {

        mainViewModel.readFavoriteRecipes.observe(this) {
            val item = it.firstOrNull { it.recipes.id == args.result.id }
            item?.let {
                mainViewModel.deleteFavorite(it)
            }
        }

        changeItemColor(item, R.color.white)
        showSnackBar("Recipe removed")
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.detailsLayout, message, Snackbar.LENGTH_SHORT).setAction("Okey"){}.show()
    }

    private fun changeItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }
}