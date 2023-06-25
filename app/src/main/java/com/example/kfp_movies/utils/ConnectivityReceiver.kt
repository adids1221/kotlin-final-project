package com.example.kfp_movies.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import com.example.kfp_movies.R

class ConnectivityReceiver(private val context: Context) : BroadcastReceiver() {
    private var isConnected = true
    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)
        }
        val isCurrentlyConnected = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if (!isConnected && isCurrentlyConnected) {
            showToast(getStringResource(R.string.internet_connected))
        } else if (isConnected && !isCurrentlyConnected) {
            showToast(getStringResource(R.string.internet_disconnected))
        }
        isConnected = isCurrentlyConnected
    }

    private fun getStringResource(resourceId: Int): String {
        return context.getString(resourceId)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

