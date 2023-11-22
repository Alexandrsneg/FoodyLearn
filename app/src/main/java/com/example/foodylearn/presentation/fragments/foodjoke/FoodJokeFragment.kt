package com.example.foodylearn.presentation.fragments.foodjoke

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.compose.ui.text.font.Font
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.foodylearn.R
import com.example.foodylearn.databinding.FragmentFoodJokeBinding
import com.example.foodylearn.databinding.FragmentRecipesBinding
import com.example.foodylearn.presentation.fragments.ABaseFragment
import com.example.foodylearn.presentation.mvi.MainScreenUserIntent
import com.example.foodylearn.presentation.viewmodels.MainViewModel
import com.example.foodylearn.util.repeatOnLifecycleExt


class FoodJokeFragment : ABaseFragment<FragmentFoodJokeBinding>(FragmentFoodJokeBinding::inflate) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScreenStateObserver()
        if (savedInstanceState == null)
            mainViewModel.onMainScreenIntent(MainScreenUserIntent.OnGetJoke)
    }

    private fun initScreenStateObserver() {
        repeatOnLifecycleExt(Lifecycle.State.STARTED) {
            mainViewModel.jokeFragmentState.collect {
                binding.greeting.apply {
                    joke = it.joke
                    visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.joke_share_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == R.id.share) {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, binding.greeting.joke)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, null))
            true
        } else {
            super.onOptionsItemSelected(item)
        }

}