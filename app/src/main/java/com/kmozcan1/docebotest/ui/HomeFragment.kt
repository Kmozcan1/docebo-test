package com.kmozcan1.docebotest.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.HomeFragmentBinding
import com.kmozcan1.docebotest.presentation.viewmodel.HomeViewModel
import com.kmozcan1.docebotest.presentation.viewstate.HomeViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun layoutId(): Int = R.layout.home_fragment

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun onViewBound() {
        binding.homeFragment = this
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        appCompatActivity.setSupportActionBar(binding.toolbar)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        setSearchView(menu.findItem(R.id.search).actionView as SearchView)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setSearchView(searchView: SearchView) {
        searchView.isIconified = true
        searchView.setIconifiedByDefault(false)
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchUser(query)
                }
                return true
            }

        })
    }

    private fun viewStateObserver() = Observer<HomeViewState> { viewState ->
        if (viewState.state == HomeViewState.State.SEARCH_RESULT) {
            makeToast("GİRDİ")
        }
        when (viewState.state) {
            HomeViewState.State.SEARCH_RESULT -> {
                makeToast("GİRDİ")
            }
            HomeViewState.State.ERROR -> {
                makeToast("ERROR")
            }
            HomeViewState.State.LOADING -> {
                makeToast("LOADING")
            }
        }
    }

}