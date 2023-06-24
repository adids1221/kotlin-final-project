package com.example.kfp_movies.ui

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.kfp_movies.R
import com.example.kfp_movies.data.local_db.AppDatabase
import com.example.kfp_movies.databinding.ActivityMainBinding
import com.example.kfp_movies.utils.ConnectivityReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var connectivityReceiver: ConnectivityReceiver
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var appDatabase: AppDatabase
    private var connectionLost: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityReceiver = ConnectivityReceiver(this)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        appDatabase = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            if (!isConnectedToInternet() && isDatabaseEmpty()) {
                connectionLost = true
                showNoConnectivityLayout()
            } else {
                showContentView()
            }
        }
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(connectivityReceiver)
    }

    private fun isConnectedToInternet(): Boolean {
        val networkCapabilities = connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)
        }
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private fun showNoConnectivityLayout() {
        val noConnectivityLayout = layoutInflater.inflate(R.layout.no_connectivity, null)
        setContentView(noConnectivityLayout)

        val tryAgainButton = noConnectivityLayout.findViewById<Button>(R.id.try_again_button)
        tryAgainButton.setOnClickListener {
            // Check for internet connectivity again when "Try Again" button is clicked
            if (isConnectedToInternet()) {
                connectionLost = false
                recreate() // Reload the activity to proceed with regular initialization
            }
        }
    }

    private suspend fun isDatabaseEmpty(): Boolean {
        val movies = appDatabase.movieDao().getAllSuspend()
        return movies.isEmpty()
    }

    private fun showContentView() {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.title = getString(R.string.app_name)
    }


}