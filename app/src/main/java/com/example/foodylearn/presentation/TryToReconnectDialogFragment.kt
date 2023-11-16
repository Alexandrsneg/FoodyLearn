package com.example.foodylearn.presentation

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.foodylearn.data.IOExceptionInterceptor
import com.example.foodylearn.data.IOInterceptorStatus
import com.example.foodylearn.databinding.FragmentRecconectDialogBinding
import com.example.foodylearn.util.extensions.animateAlpha
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TryToReconnectDialogFragment :
    BaseDialogFragment<FragmentRecconectDialogBinding>(
        FragmentRecconectDialogBinding::inflate,
        removeDefaultBackground = true
    ) {

    @Inject
    lateinit var ioExceptionInterceptor: IOExceptionInterceptor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        initListeners()
        addObservers()
    }

    private fun initListeners() {
        binding.btnRetry.setOnClickListener {
            getRetryBtnBlock()?.invoke()
        }

        binding.btnTerminate.setOnTapCompleteListener {
            ioExceptionInterceptor.isCanceled = true
            goToLogin()
        }
    }


    private fun addObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                ioExceptionInterceptor.status.collectLatest { status ->
                    if (status.tryCount.toString() != binding.tvAttempt.text) {
                        binding.tvAttempt.text = "${status.tryCount}"
                        binding.tvAttemptTotal.text = IOExceptionInterceptor.MAX_TRY.toString()
                        renderVisibility(true)
                    }

                    when (status.status) {
                        IOInterceptorStatus.OnAttemptsEnded ->  {
                            renderVisibility(false)
                        }
                        IOInterceptorStatus.OnSuccessResponse -> {
                            dismiss()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun renderVisibility(isProgressVisible: Boolean) {
        binding.attemptsContainer.animateAlpha(isIn = isProgressVisible, fullInvise = true)
        binding.btnRetry.animateAlpha(isIn = !isProgressVisible, fullInvise = true)
    }


    private fun goToLogin() {
        println("implement exit to login")

    }

    private fun getRetryBtnBlock(): (() -> Unit)? {
        val map = arguments?.getSerializable(ARG_BTN_MAP) as? HashMap<String, () -> Unit>
        return map?.get(ARG_RETRY_BTN_BLOCK)
    }

    override fun resizeDialog(widthDp: Int?, heightDp: Int?) {
        if (widthDp == null && heightDp == null) {
            val orientation = context?.resources?.configuration?.orientation
            val multiplier = if (orientation == Configuration.ORIENTATION_PORTRAIT) 0.6 else 0.8
            val newHeight = Resources.getSystem().displayMetrics.heightPixels * multiplier
            val newWidth = Resources.getSystem().displayMetrics.widthPixels * 0.9
            dialog?.window?.setLayout(newWidth.toInt(), newHeight.toInt())
        } else {
            if (widthDp == null || heightDp == null)
                return
            dialog?.window?.setLayout(
                widthDp * resources.displayMetrics.density.toInt(),
                heightDp * resources.displayMetrics.density.toInt()
            )
        }
    }

    companion object {
        const val TAG_RECONNECT_DIALOG = "TAG_RECONNECT_DIALOG"
        private const val ARG_RETRY_BTN_BLOCK = "ARG_RETRY_BTN_BLOCK"
        private const val ARG_BTN_MAP = "ARG_BTN_MAP"

        fun find(activity: FragmentActivity): TryToReconnectDialogFragment? =
            activity.supportFragmentManager.findFragmentByTag(TAG_RECONNECT_DIALOG) as? TryToReconnectDialogFragment

        fun newInstance(retryBtnBlock: () -> Unit) = TryToReconnectDialogFragment().apply {
            arguments = bundleOf(
                ARG_BTN_MAP to hashMapOf(
                    ARG_RETRY_BTN_BLOCK to retryBtnBlock
                )
            )
        }
    }

}