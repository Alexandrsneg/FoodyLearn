package com.example.foodylearn.presentation

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<VB : ViewBinding>(
    private val inflaterBinding: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val removeDefaultBackground: Boolean
) : DialogFragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflaterBinding.invoke(inflater, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
        if (removeDefaultBackground)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        return dialog
    }

    open fun resizeDialog(widthDp: Int? = null, heightDp: Int? = null) {
        if (widthDp == null && heightDp == null) {
            val newWidth = Resources.getSystem().displayMetrics.widthPixels * 0.9
            val newHeight = Resources.getSystem().displayMetrics.heightPixels * 0.9
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


    open fun setupViews() {}
    open fun setupObservers() {}

    override fun onResume() {
        super.onResume()
        resizeDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
