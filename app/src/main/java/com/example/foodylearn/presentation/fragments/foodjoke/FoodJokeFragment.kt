package com.example.foodylearn.presentation.fragments.foodjoke

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.foodylearn.R
import com.example.foodylearn.databinding.FragmentFoodJokeBinding
import com.example.foodylearn.presentation.fragments.ABaseFragment
import com.example.foodylearn.presentation.mvi.UserIntent
import com.example.foodylearn.presentation.viewmodels.MainViewModel
import com.example.foodylearn.util.extensions.animateAlpha
import com.example.foodylearn.util.extensions.visibility
import com.example.foodylearn.util.repeatOnLifecycleExt


class FoodJokeFragment : ABaseFragment<FragmentFoodJokeBinding>(FragmentFoodJokeBinding::inflate) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScreenStateObserver()
        initListeners()
        if (savedInstanceState == null)
            getJoke()
    }

    private fun initListeners() {
        binding.btnRefresh.setOnClickListener {
            getJoke()
        }
    }

    private fun initScreenStateObserver() {
        repeatOnLifecycleExt(Lifecycle.State.STARTED) {
            mainViewModel.jokeFragmentState.collect {
                binding.ivJokeNotFound.visibility(it.error != null)
                binding.progressBar.visibility(it.isLoading)
                binding.greeting.apply {
                    joke = it.joke
                    animateAlpha(!it.isLoading)
                }
            }
        }
    }

    private fun getJoke() {
        mainViewModel.onMainScreenIntent(UserIntent.OnGetJoke)
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