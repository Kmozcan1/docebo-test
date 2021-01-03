package com.kmozcan1.docebotest.ui

import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.HomeFragmentBinding
import com.kmozcan1.docebotest.presentation.adapter.UserListAdapter
import com.kmozcan1.docebotest.presentation.setAdapter
import com.kmozcan1.docebotest.presentation.viewmodel.HomeViewModel
import com.kmozcan1.docebotest.presentation.viewstate.HomeViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    // RecyclerView Adapter
    private val userListAdapter: UserListAdapter by lazy {
        UserListAdapter(mutableListOf())
    }

    override fun layoutId(): Int = R.layout.home_fragment

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun onViewBound() {
        binding.homeFragment = this

        setToolbar()

        setUserList()
    }

    // Sets the toolbar with options menu
    private fun setToolbar() {
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        appCompatActivity.setSupportActionBar(binding.toolbar)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)
    }

    // Sets the paginated list adapter and callback listener
    private fun setUserList() {
        binding.userListView.setAdapter(
            LinearLayoutManager(context),
            userListAdapter
        )

        binding.userListView.setCallbackListener(userListCallbackListener())
    }

    override fun observeLiveDate() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        setSearchView(menu.findItem(R.id.search).actionView as SearchView)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setSearchView(searchView: SearchView) {
        // Sets searchView to be always extended
        searchView.isIconified = true
        searchView.setIconifiedByDefault(false)
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(onQueryTextListener())
    }

    // Returns paginated list callback listener object
    private fun userListCallbackListener() = object : PaginatedListView.CallbackListener {
        override fun onPaginatedListFinalItemVisible() {
            binding.userListView.showProgressBar(true)
            viewModel.loadMoreResults()
        }
    }

    // Returns ViewState Observer object
    private fun viewStateObserver() = Observer<HomeViewState> { viewState ->
        when (viewState.state) {
            HomeViewState.State.SEARCH_RESULT -> {
                // Hide progress bar
                binding.userListView.showProgressBar(false)
                with(viewState.userSearchResult!!) {
                    userListAdapter.addSearchResult(userList)
                    binding.userListView.onFinalPage = finalPage
                }

            }
            HomeViewState.State.ERROR -> {
                makeToast(viewState.errorMessage)
            }
            HomeViewState.State.LOADING -> {
                makeToast("LOADING")
            }
        }
    }

    // Returns SearchView text listener object
    private fun onQueryTextListener() = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query != null) {
                userListAdapter.clearSearchResults()
                viewModel.searchUser(query)
            }
            return true
        }
    }

}