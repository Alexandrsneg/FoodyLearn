package com.example.foodylearn.util.extensions

import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButtonToggleGroup

inline fun MaterialButtonToggleGroup.onCheckChangeListener(
    crossinline block: (Int, Boolean) -> Unit,
) {
    addOnButtonCheckedListener { group, checkedId, isChecked ->
        if (checkedButtonId != checkedId) {
            return@addOnButtonCheckedListener
        } else {
            block.invoke(checkedId, isChecked)
        }
    }
}

fun MaterialButtonToggleGroup.setEnabledAlpha(enabled: Boolean) {
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        view.isEnabled = enabled
    }
    alpha = if (enabled) {
        1f
    } else {
        0.5f
    }
}


fun View.fadeVisibilityAnimate(show: Boolean, duration: Long = 300) {
    val transition: Transition = Fade().apply {
        this.duration = duration
        addTarget(this@fadeVisibilityAnimate)
    }
    TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.enabledAlpha() {
    visibility = View.VISIBLE
    alpha = 1f
    isEnabled = true
}

fun List<View>.disableAnimated(disable: Boolean, onAnimationEnd: (() -> Unit)? = null) {
    forEach {
        it.animateAlpha(!disable, onAnimationEnd)
        it.isEnabled = !disable
    }
}

fun View.disableAnimated(disable: Boolean, onAnimationEnd: (() -> Unit)? = null) {
    animateAlpha(!disable, onAnimationEnd)
    isEnabled = !disable
}

fun View.animateAlpha(isIn: Boolean, onAnimationEnd: (() -> Unit)? = null) {
    val endAlpha = if (isIn) 1f else 0.5f
    animate().alpha(endAlpha).setDuration(300).withEndAction {
        onAnimationEnd?.invoke()
    }
}