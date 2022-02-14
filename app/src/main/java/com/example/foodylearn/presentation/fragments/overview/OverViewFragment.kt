package com.example.foodylearn.presentation.fragments.overview

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.foodylearn.databinding.FragmentOverViewBinding
import com.example.foodylearn.models.Result


class OverViewFragment : Fragment() {

    private var firstTriggered = false

    private var _binding: FragmentOverViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOverViewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")

        myBundle?.let {
            with(view) {
                binding.ivMainImage.load(it.image)
                binding.tvTitle.text = it.title
                binding.tvFavorites.text = it.aggregateLikes.toString()
                binding.tvMinutes.text = it.readyInMinutes.toString()
                binding.tvTitle.text = it.title
                binding.tvSummary.text = Html.fromHtml(it.summary)
//                binding.tvSummaryText.text = Html.fromHtml(it.summary)

                if (it.vegetarian)
                    binding.cvVegetarian.active = true

                if (it.vegan)
                    binding.cvVegan.active = true

                if (it.glutenFree == true)
                    binding.cvGlutenFree.active = true

                if (it.dairyFree == true)
                   binding.cvDairyFree.active = true

                if (it.veryHealthy)
                    binding.cvHealthy.active = true

                if (it.cheap == true)
                    binding.cvCheap.active = true

//                scrollView3.viewTreeObserver.addOnScrollChangedListener {
//
//
//                    if (!scrollView3.canScrollVertically(-1) && mlRoot.progress == 1F){
//                        // top of scroll view
//                        mlRoot.transitionToStart()
//                    }
//                }
//                mlRoot.addTransitionListener(object : MotionLayout.TransitionListener{
//                    override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}
//                    override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {}
//                    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
//                        if (mlRoot.progress == 1F) {
//                            tvSummaryText.visibility = View.INVISIBLE
//                            scrollView3.visibility = View.VISIBLE
//                        }
//                        if (mlRoot.progress == 0F) {
//                            tvSummaryText.visibility = View.VISIBLE
//                            scrollView3.visibility = View.INVISIBLE
//                        }
//
//                    }
//
//                    override fun onTransitionTrigger(
//                        motionLayout: MotionLayout?,
//                        triggerId: Int,
//                        positive: Boolean,
//                        progress: Float
//                    ) {
//                    }
//
//                })
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    binding.scrollView3.setOnScrollChangeListener { view, scrollX, scrollY, oldX, oldY ->
////                        if (scrollY >= oldY && mlRoot.progress == 0F) {
////                            //на верху
////                            tvSummaryText.visibility = View.VISIBLE
////                            scrollView3.visibility = View.INVISIBLE
////                            mlRoot.transitionToEnd()
////                        }
//
//                        if (!binding.scrollView3.canScrollVertically(-1) && binding.mlRoot.progress == 1F) {
//                            // top of scroll view
//                            binding.mlRoot.transitionToStart()
////                            tvSummaryText.visibility = View.VISIBLE
////                            scrollView3.visibility = View.INVISIBLE
//                        }
//
//                    }
//                }
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}