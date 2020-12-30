package com.kmozcan1.docebotest.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.ui.setupWithNavController
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.HomeFragmentBinding
import com.kmozcan1.docebotest.presentation.viewmodel.HomeViewModel
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        setSearchView(menu.findItem(R.id.search).actionView as SearchView)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setSearchView(searchView: SearchView) {
        searchView.queryHint = "Your option query hint"
        searchView.isIconified = true
        searchView.setIconifiedByDefault(false)
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                var a = 1
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

}