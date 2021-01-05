package com.kmozcan1.docebotest.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.ActivityMainBinding
import com.kmozcan1.docebotest.presentation.viewmodel.MainViewModel
import com.kmozcan1.docebotest.presentation.viewstate.MainViewState
import com.kmozcan1.docebotest.presentation.viewstate.MainViewState.State.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    var isConnectedToInternet: Boolean = false
        private set

    private lateinit var binding: ActivityMainBinding

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.viewState.observe(this, observeViewState())
        viewModel.observeInternetConnection()

        /*val toolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.coll)
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        val appBarConfiguration = AppBarConfiguration(navController.graph)*/


    }

    private fun observeViewState() = Observer<MainViewState> { viewState ->
        when (viewState.state) {
            ERROR -> {
                makeToast(viewState.errorMessage)
            }
            CONNECTION_CHANGE -> {
                val baseFragment = supportFragmentManager.fragments.first()?.childFragmentManager?.fragments?.get(0) as BaseFragment<*, *>
                isConnectedToInternet = viewState.isConnected
                if (viewState.isConnected) {
                    baseFragment.onInternetConnected()
                } else {
                    baseFragment.onInternetDisconnected()
                }
            }
            LOADING -> TODO()
        }
    }

    fun makeToast(toastMessage: String?) {
        Toast.makeText(
            this,
            toastMessage,
            Toast.LENGTH_LONG
        ).show()
    }
}