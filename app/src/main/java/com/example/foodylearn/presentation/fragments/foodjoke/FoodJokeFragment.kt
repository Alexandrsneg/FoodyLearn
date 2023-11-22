package com.example.foodylearn.presentation.fragments.foodjoke

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.compose.ui.text.font.Font
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.foodylearn.R
import com.example.foodylearn.databinding.FragmentFoodJokeBinding
import com.example.domain.models.FoodJoke
import com.example.domain.models.NetworkResult
import com.example.foodylearn.presentation.mvi.MainScreenUserIntent
import com.example.foodylearn.presentation.viewmodels.MainViewModel
import com.example.foodylearn.util.repeatOnLifecycleExt
import java.util.*


class FoodJokeFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setHasOptionsMenu(true)
        val font = Font(R.font.courgette)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)

        if (savedInstanceState == null)
            mainViewModel.onMainScreenIntent(MainScreenUserIntent.OnGetJoke)

        repeatOnLifecycleExt(Lifecycle.State.STARTED) {
            mainViewModel.jokeFragmentState.collect {
                binding.greeting.apply {
                    joke = it.joke
                    visibility = View.VISIBLE
                }
            }
        }


        if (!NotificationManagerCompat.from(requireContext()).areNotificationsEnabled())
            binding.btnNotifications.visibility = View.VISIBLE

        binding.btnNotifications.setOnClickListener {
            val intent = Intent()
            context?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, it.packageName)
                } else {
                    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                    intent.putExtra("app_package", it.packageName)
                    intent.putExtra("app_uid", it.applicationInfo.uid)
                }
                requireContext().startActivity(intent)
            }
        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.joke_share_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.share) {
            binding.greeting.joke.let {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, it)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!NotificationManagerCompat.from(requireContext()).areNotificationsEnabled())
            binding.btnNotifications.visibility = View.VISIBLE
        else
            binding.btnNotifications.visibility = View.GONE
    }
}