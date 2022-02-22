package com.example.foodylearn.presentation.fragments.foodjoke

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.foodylearn.R
import com.example.foodylearn.data.database.joke.JokeEntity
import com.example.foodylearn.databinding.FragmentFoodJokeBinding
import com.example.foodylearn.models.FoodJoke
import com.example.foodylearn.util.NetworkResult
import com.example.foodylearn.viewmodels.MainViewModel
import java.util.*


class FoodJokeFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)

        mainViewModel.getJoke()
        mainViewModel.jokeResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.tvJoke.apply {
                        text = it.data?.text
                        visibility = View.VISIBLE
                    }
                    binding.pbCircleProgress.visibility = View.GONE
                    mainViewModel.insertJoke(JokeEntity(1, FoodJoke(it.data?.text)))
                }
                is NetworkResult.Error -> {
                    getJokeFromCache()
                }
                is NetworkResult.Loading -> {
                    binding.pbCircleProgress.visibility = View.VISIBLE
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

    private fun getJokeFromCache() {
        mainViewModel.readJoke.observe(viewLifecycleOwner) {
            it?.let { joke ->
                binding.ivJokeNotFound.visibility = View.GONE
                binding.tvJoke.apply {
                    text = joke.joke.text
                    visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.joke_share_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.share) {
            binding.tvJoke.text?.let {
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
//
//    @Composable
//    private fun Greeting() {
//        Text(
//            text = "Joke",
//            style = MaterialTheme.typography.h5,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 8.dp)
//                .wrapContentWidth(Alignment.CenterHorizontally)
//        )
//    }


}