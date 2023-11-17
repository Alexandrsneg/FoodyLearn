package com.example.foodylearn.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NetworkListener(context: Context) : ConnectivityManager.NetworkCallback() {

    private val _isNetworkAvailable = MutableStateFlow(false)
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable

    override fun onAvailable(network: Network) {
        _isNetworkAvailable.update { true }
    }
    override fun onLost(network: Network) {
        _isNetworkAvailable.update { false }
    }
    init {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            connectivityManager.registerDefaultNetworkCallback(this)
        else
            connectivityManager.registerNetworkCallback(networkRequest, this)
    }

}