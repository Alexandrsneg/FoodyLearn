package com.example.foodylearn.presentation.views

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.example.foodylearn.R
import com.example.foodylearn.databinding.LongTapButtonBinding


class LongTapButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {
    private var binding: LongTapButtonBinding
    private var timer: Float = 1000F
    private var onTapComplete: (() -> Unit)? = null

    fun setOnTapCompleteListener(listener: () -> Unit): (() -> Unit) {
        onTapComplete = listener
        return onTapComplete as () -> Unit
    }

    init {
        binding = LongTapButtonBinding.inflate(LayoutInflater.from(context), this, true)
        context.withStyledAttributes(attrs, R.styleable.LongTapButton, defStyleAttrs) {
            timer = getFloat(R.styleable.LongTapButton_ltp_timer_ms, 1000F)
            val title = getString(R.styleable.LongTapButton_ltp_title)
                ?: "Exit"
            binding.tvTitle.text = title
        }

        setOnTouchListener { v, event ->
            if (event?.action == MotionEvent.ACTION_DOWN) {
                performClick()
                countDownTimer.start()
            }
            if (event?.action == MotionEvent.ACTION_UP) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    binding.progressBar.setProgress(0, true)
                else
                    binding.progressBar.progress = 0
                countDownTimer.cancel()
            }
            true
        }
    }

    private val countDownTimer by lazy {
        object : CountDownTimer(timer.toLong(), 10L) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (timer - millisUntilFinished) / timer * 100
                binding.progressBar.progress = progress.toInt()
            }

            override fun onFinish() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    binding.progressBar.setProgress(0, true)
                 else
                    binding.progressBar.progress = 0

                onTapComplete?.invoke()
            }

        }
    }
}
