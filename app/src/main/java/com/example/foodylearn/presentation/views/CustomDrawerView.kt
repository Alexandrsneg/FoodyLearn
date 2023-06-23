package com.example.foodylearn.presentation.views


import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.foodylearn.R
import com.example.foodylearn.databinding.CutomDrawerViewBinding


class CustomDrawerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs) {

    private var binding: CutomDrawerViewBinding
    private var mainFragmentContainer: FrameLayout? = null
    private var infoFragmentContainer: FrameLayout? = null

    private var isInfoPanelHidden: Boolean = true
    private var hostActivityIntent: Intent? = null

    init {
        isSaveEnabled = true
        binding = CutomDrawerViewBinding.inflate(LayoutInflater.from(context), this, true)
        mainFragmentContainer = findViewById(R.id.flMainFragmentContainer)
        infoFragmentContainer = findViewById(R.id.llContainer)

        setTransitionListener()
        setDrawerWidth()
        binding.button.setOnClickListener {
            renderVisibilityDrawerState(isInfoPanelHidden)
        }
    }


    fun hide() {
        renderVisibilityDrawerState(false)
    }

    fun show() {
        renderVisibilityDrawerState(true)
    }

    fun isAlreadyClosed(): Boolean {
        hide()
        return isInfoPanelHidden
    }


    fun checkInfoBoardVisibleState(intent: Intent) {
        this.hostActivityIntent = intent
        if (intent.getBooleanExtra(IS_INFO_VISIBLE, false))
            show()
        else
            hide()
    }


    private fun setTransitionListener() {
        binding.root.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                hostActivityIntent?.putExtra(IS_INFO_VISIBLE, currentId == R.id.show)
                isInfoPanelHidden = currentId == R.id.hide
                if (isInfoPanelHidden)
                    binding.ivArrow.rotation = 360F
                else
                    binding.ivArrow.rotation = 180F
            }

            override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}
            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {}
            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {}
        })
    }

    private fun setDrawerWidth() {
        val width = (resources.displayMetrics.widthPixels * 0.8).toInt()
        binding.frameLayout.layoutParams = ConstraintLayout.LayoutParams(
            width,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )

        binding.root.getConstraintSet(R.id.hide)?.constrainWidth(R.id.frameLayout, width)
        binding.root.getConstraintSet(R.id.show)?.constrainWidth(R.id.frameLayout, width)
    }


    private fun renderVisibilityDrawerState(visibility: Boolean) {
        with(binding) {
            if (visibility) root.transitionToEnd() else root.transitionToStart()
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        updateGestureExclusion()
    }

    private fun updateGestureExclusion() {
        if (Build.VERSION.SDK_INT < 29) return
        val gestureExclusionRects = mutableListOf<Rect>()
        with(binding.button) {
            gestureExclusionRects.add(Rect(left,top, right, bottom))
        }
        systemGestureExclusionRects = gestureExclusionRects
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        when {
            mainFragmentContainer == null -> super.addView(child, index, params);
            child?.tag == "main_fragment" -> binding.flMainFragmentContainer.addView(
                child,
                index,
                params
            )
            child?.tag == "info_fragment" -> binding.llContainer.addView(child, index, params);
        }
    }

    companion object {
        private const val IS_INFO_VISIBLE = "IIV"

    }
}
