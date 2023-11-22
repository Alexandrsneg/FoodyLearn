package com.example.foodylearn.presentation.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.example.foodylearn.R
import com.example.foodylearn.util.NetworkListener
import com.example.foodylearn.util.repeatOnLifecycleExt
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

abstract class ABaseActivity<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> VB
) : AppCompatActivity() {

    @Inject
    lateinit var networkListener: NetworkListener

    private lateinit var _binding: ViewBinding

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(_binding.root)

        repeatOnLifecycleExt(Lifecycle.State.STARTED) {
            networkListener.isNetworkAvailable.collect { isAvailable ->
                if (connectionStatusIsDifferent(isAvailable)) {
                    val message = if (isAvailable) getString(R.string.connection_available)
                    else getString(R.string.connection_lost)
                    Toast.makeText(this@ABaseActivity, message, Toast.LENGTH_SHORT).show()
                }
                saveLastConnectionStatus(isAvailable)
            }
        }
    }

    fun showSnackBar(
        message: String,
        actionText: String = "OK",
        anchorView: View = binding.root
    ) {
        Snackbar.make(anchorView, message, Snackbar.LENGTH_SHORT).setAction(actionText) {}.show()
    }

    companion object {
        private const val NETWORK_CONNECTION_STATUS = "NETWORK_CONNECTION_STATUS"
    }

    private fun saveLastConnectionStatus(status: Boolean) {
        intent.putExtra(NETWORK_CONNECTION_STATUS, status)
    }

    private fun connectionStatusIsDifferent(status: Boolean): Boolean =
        status != intent.getBooleanExtra(NETWORK_CONNECTION_STATUS, true)
}