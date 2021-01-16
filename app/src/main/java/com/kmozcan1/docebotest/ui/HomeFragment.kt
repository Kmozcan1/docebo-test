package com.kmozcan1.docebotest.ui

import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.HomeFragmentBinding
import com.kmozcan1.docebotest.presentation.adapter.UserListAdapter
import com.kmozcan1.docebotest.presentation.hideKeyboard
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

    private var searchQuery: String = ""

    private lateinit var searchBar: SearchView

    // To prevent multiple search during load
    private var searchDisabled = true

    // RecyclerView Adapter
    private val userListAdapter: UserListAdapter by lazy {
        UserListAdapter(mutableListOf(), userListCallbackListener)
    }

    override val layoutId: Int = R.layout.home_fragment

    override val viewModelClass: Class<HomeViewModel> = HomeViewModel::class.java


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

    override fun observeLiveData() {
        // Observes the ViewState
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
    }

    // Adds search bar to the toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        searchBar = menu.findItem(R.id.search).actionView as SearchView
        setSearchBar()
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Sets searchView to be always extended, sets the hint and text listener
    private fun setSearchBar() {
        searchBar.run {
            isIconified = true
            setIconifiedByDefault(false)
            maxWidth = Integer.MAX_VALUE
            queryHint = getString(R.string.search_user)
            setOnQueryTextListener(onQueryTextListener())
        }
    }

    // Returns paginated list callback listener object
    private fun userListCallbackListener() = object : PaginatedListView.CallbackListener {
        // Loads more results when the final item has been reached
        // (only invoked if finalPage = false)
        override fun onPaginatedListFinalItemVisible() {
            if (userListAdapter.itemCount != 0) {
                binding.userListView.showBottomProgressBar(true)
            }
            viewModel.loadMoreResults()
        }

        override fun onPaginatedListItemClick(userName: String) {
            hideKeyboard()
            viewModel.hasRetainedList = true
            searchBar.visibility = View.GONE
            val navAction =  HomeFragmentDirections
                    .actionHomeFragmentToUserViewPagerFragment(userName)
            navController.navigate(navAction)
        }
    }

    // Returns ViewState Observer object
    private fun viewStateObserver() = Observer<HomeViewState> { viewState ->
        when (viewState.state) {
            State.INITIAL -> {
                if (getIsConnectedToInternet()) {
                    viewModel.searchUser()
                }
            }
            State.SEARCH_RESULT -> {
                with(binding.userListView) {
                    // Hides the progress bars
                    showTopProgressBar(false)
                    showBottomProgressBar(false)

                    viewState.userSearchResult?.let { searchResult ->
                        // Shows empty text if no results are returned
                        if (searchResult.userList.isEmpty()) {
                            setEmptyText(context.getString(R.string.home_empty_result, searchQuery))
                            showEmptyText(true)
                        }

                        // Adds the results to the RecyclerView
                        // If the user is navigating from the profile screen, adds all results to the
                        // RecyclerView, instead of just adding the latest results
                        if (!viewModel.hasRetainedList) {
                            userListAdapter.addSearchResult(searchResult.userList)
                        } else {
                            viewModel.hasRetainedList = false
                            userListAdapter.addSearchResult(viewState.allSearchResults!!)
                            onFinalPage = searchResult.finalPage
                        }
                        searchDisabled = false
                    }
                }
            }
            State.ERROR -> {
                makeToast(viewState.errorMessage)
                binding.userListView.showTopProgressBar(false)
                searchDisabled = false
            }
            State.LOADING -> {
                // Show progress bar and hide empty text on load
                searchDisabled = true
                with(binding.userListView) {
                    showEmptyText(false)
                    showTopProgressBar(true)
                }
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
                if (getIsConnectedToInternet()) {
                    searchQuery = query
                    userListAdapter.clearSearchResults()
                    viewModel.searchUser(query)
                    searchDisabled = true
                    hideKeyboard()
                } else {
                    with(binding.userListView) {
                        setEmptyText(context.getString(R.string.internet_disconnected))
                        showEmptyText(true)
                    }
                }
            }
            return true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        userListAdapter.clearSearchResults()
    }

    override fun onInternetDisconnected() {
        with(binding.userListView) {
            setEmptyText(context.getString(R.string.internet_disconnected))
            showEmptyText(true)
        }
        super.onInternetDisconnected()
    }

    override fun onInternetConnected() {
        if (previouslyDisconnected) {
            binding.userListView.showEmptyText(false)
        }
        if (previouslyDisconnected && userListAdapter.userList.isEmpty()) {
            viewModel.searchUser(searchQuery)
        }
        super.onInternetConnected()
    }

}