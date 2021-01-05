package com.kmozcan1.docebotest.presentation.ui

import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.HomeFragmentBinding
import com.kmozcan1.docebotest.presentation.adapter.UserListAdapter
import com.kmozcan1.docebotest.presentation.viewmodel.HomeViewModel
import com.kmozcan1.docebotest.presentation.viewstate.HomeViewState
import com.kmozcan1.docebotest.presentation.viewstate.HomeViewState.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val userListCallbackListener = userListCallbackListener()

    // RecyclerView Adapter
    private val userListAdapter: UserListAdapter by lazy {
        UserListAdapter(mutableListOf(), userListCallbackListener)
    }

    override fun layoutId(): Int = R.layout.home_fragment

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun onViewBound() {
        setToolbar()

        setUserList()
    }

    // Sets the toolbar with options menu
    private fun setToolbar() {
        binding.homeToolbar.setupWithNavController(navController, appBarConfiguration)
        appCompatActivity.setSupportActionBar(binding.homeToolbar)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)
    }

    // Sets the paginated list adapter and callback listener
    private fun setUserList() {
        with(binding) {
            userListView.setAdapter(
                LinearLayoutManager(context),
                userListAdapter
            )

            userListView.setCallbackListener(userListCallbackListener)
        }
    }

    override fun observeLiveDate() {
        // Observes the ViewState
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
        // Loads more results when the final item has been reached
        // (only invoked if finalPage = false)
        override fun onPaginatedListFinalItemVisible() {
            binding.userListView.showProgressBar(true)
            viewModel.loadMoreResults()
        }

        override fun onPaginatedListItemClick(userName: String) {
            val navAction =  HomeFragmentDirections
                    .actionHomeFragmentToUserViewPagerFragment(userName)
            navController.navigate(navAction)
        }
    }

    // Returns ViewState Observer object
    private fun viewStateObserver() = Observer<HomeViewState> { viewState ->
        when (viewState.state) {
            State.SEARCH_RESULT -> {
                with(binding.userListView) {
                    // Hides the progress bar at the end of the paginated list
                    showProgressBar(false)
                    // Add results
                    viewState.userSearchResult?.let { searchResult ->
                        userListAdapter.addSearchResult(searchResult.userList)
                        onFinalPage = searchResult.finalPage
                    }
                }
            }
            State.ERROR -> {
                makeToast(viewState.errorMessage)
            }
            State.LOADING -> {
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