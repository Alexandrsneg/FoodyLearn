package com.example.foodylearn.presentation.fragments.overview

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.constraintlayout.motion.widget.MotionLayout
import coil.load
import com.example.foodylearn.R
import com.example.foodylearn.models.Result
import kotlinx.android.synthetic.main.fragment_over_view.view.*


class OverViewFragment : Fragment() {

    private var firstTriggered = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_over_view, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")

        myBundle?.let {
            with(view) {
                ivMainImage.load(it.image)
                tvTitle.text = it.title
                tvFavorites.text = it.aggregateLikes.toString()
                tvMinutes.text = it.readyInMinutes.toString()
                tvTitle.text = it.title
                tvSummary.text = Html.fromHtml(it.summary)
                tvSummaryText.text = Html.fromHtml(it.summary)

                if (it.vegetarian)
                    cvVegetarian.active = true

                if (it.vegan)
                    cvVegan.active = true

                if (it.glutenFree == true)
                    cvGlutenFree.active = true

                if (it.dairyFree == true)
                    cvDairyFree.active = true

                if (it.veryHealthy)
                    cvHealthy.active = true

                if (it.cheap == true)
                    cvCheap.active = true

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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    scrollView3.setOnScrollChangeListener { view, scrollX, scrollY, oldX, oldY ->
//                        if (scrollY >= oldY && mlRoot.progress == 0F) {
//                            //на верху
//                            tvSummaryText.visibility = View.VISIBLE
//                            scrollView3.visibility = View.INVISIBLE
//                            mlRoot.transitionToEnd()
//                        }

                        if (!scrollView3.canScrollVertically(-1) && mlRoot.progress == 1F) {
                            // top of scroll view
                            mlRoot.transitionToStart()
//                            tvSummaryText.visibility = View.VISIBLE
//                            scrollView3.visibility = View.INVISIBLE
                        }

                    }
                }
            }
        }


        return view
    }
}