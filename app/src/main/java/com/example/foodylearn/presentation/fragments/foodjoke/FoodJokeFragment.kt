package com.example.foodylearn.presentation.fragments.foodjoke

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.foodylearn.R
import com.example.foodylearn.data.database.joke.JokeEntity
import com.example.foodylearn.models.FoodJoke
import com.example.foodylearn.util.NetworkResult
import com.example.foodylearn.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_food_joke.view.*

class FoodJokeFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_food_joke, container, false)

        mainViewModel.getJoke()
        mainViewModel.jokeResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    mView?.tvJoke?.apply {
                        text = it.data?.text
                        visibility = View.VISIBLE
                    }
                    mView?.pbCircleProgress?.visibility = View.GONE
                    mainViewModel.insertJoke(JokeEntity(1, FoodJoke(it.data?.text)))
                }
                is NetworkResult.Error -> {
                    getJokeFromCache()
                }
                is NetworkResult.Loading -> {
                    mView?.pbCircleProgress?.visibility = View.VISIBLE
                }
            }
        }

        return mView
    }

    private fun getJokeFromCache() {
        mainViewModel.readJoke.observe(viewLifecycleOwner) {
            it?.let { joke ->
                mView?.ivJokeNotFound?.visibility = View.GONE
                mView?.tvJoke?.apply {
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
            mView?.tvJoke?.text?.let {
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


}