package com.example.foodylearn.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodylearn.R
import com.example.foodylearn.databinding.ActivityMainBinding
import com.example.foodylearn.util.NetworkListener
import com.example.foodylearn.util.repeatOnLifecycleExt
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ABaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.recipesFragment,
                R.id.favoriteRecipesFragment,
                R.id.foodJokeFragment
            )
        )

        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()
}