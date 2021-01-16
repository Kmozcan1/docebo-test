package com.kmozcan1.docebotest.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.RepositoriesFragmentBinding
import com.kmozcan1.docebotest.domain.enums.SortDirection
import com.kmozcan1.docebotest.domain.enums.SortType
import com.kmozcan1.docebotest.presentation.ArgConstants
import com.kmozcan1.docebotest.presentation.adapter.RepositoryListAdapter
import com.kmozcan1.docebotest.presentation.viewmodel.RepositoriesViewModel
import com.kmozcan1.docebotest.presentation.viewstate.RepositoriesViewState
import com.kmozcan1.docebotest.presentation.viewstate.RepositoriesViewState.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesFragment : BaseFragment<RepositoriesFragmentBinding, RepositoriesViewModel>() {

    companion object {
        fun newInstance() = RepositoriesFragment()
    }

    // Api call parameters
    private lateinit var userName: String
    private var sortType = SortType.ALPHABETIC
    private var sortDirection = SortDirection.ASCENDING

    // To prevent multiple click to sort buttons while repositories are still loading
    private var sortDisabled = true

    private val repositoryListCallbackListener = repositoryListCallbackListener()

    // RecyclerView Adapter
    private val repositoryListAdapter: RepositoryListAdapter by lazy {
        RepositoryListAdapter(mutableListOf(), repositoryListCallbackListener)
    }

    private val sortBottomSheetFragment: RepoSortBottomSheetFragment by lazy {
        RepoSortBottomSheetFragment()
    }

    override val layoutId = R.layout.repositories_fragment

    override val viewModelClass: Class<RepositoriesViewModel> =
        RepositoriesViewModel::class.java

    override fun onViewBound() {
        binding.repositoriesFragmentBinding = this

        // Gets user name from bundle
        arguments?.takeIf {
            it.containsKey(ArgConstants.USER_NAME_ARG)
        }?.apply {
            userName = getString(ArgConstants.USER_NAME_ARG).toString()
        }

        setRepositoryList()
    }

    // Sets repository list adapter and listener
    private fun setRepositoryList() {
        with(binding) {
            repositoriesListView.setAdapter(
                LinearLayoutManager(context),
                repositoryListAdapter
            )

            repositoriesListView.setCallbackListener(repositoryListCallbackListener)
        }
    }

    override fun observeLiveData() {
        // Observes the ViewState
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
        getRepositories()
    }

    // Returns paginated list callback listener object
    private fun repositoryListCallbackListener() = object : PaginatedListView.CallbackListener {
        // Loads more results when the final item has been reached
        // (only invoked if finalPage = false)
        override fun onPaginatedListFinalItemVisible() {
            if (repositoryListAdapter.itemCount != 0) {
                binding.repositoriesListView.showBottomProgressBar(true)
            }
            viewModel.loadMoreResults()
        }

        override fun onPaginatedListItemClick(userName: String) {
            // No click event at the moment
        }
    }

    // Returns ViewState Observer object
    private fun viewStateObserver() = Observer<RepositoriesViewState> { viewState ->
        when (viewState.state) {
            State.REPOSITORY_RESULT -> {
                with(binding.repositoriesListView) {
                    // Hides the progress bars
                    showTopProgressBar(false)
                    showBottomProgressBar(false)
                    viewState.repositoriesResult?.let { repositoriesResult ->
                        // Shows empty text if no results are returned
                        if (repositoriesResult.repositoryList.isEmpty()) {
                            setEmptyText(context.getString(R.string.repository_empty_text))
                            showEmptyText(true)
                        }
                        // Adds results to the RecyclerView
                        repositoryListAdapter.addRepositories(repositoriesResult.repositoryList)
                        onFinalPage = repositoriesResult.finalPage
                        // Enable sorting
                        sortDisabled = false
                    }
                }
            }
            State.ERROR -> {
                makeToast(viewState.errorMessage)
                sortDisabled = false
            }
            State.LOADING -> {
                // Show progress bar and hide empty text on load
                sortDisabled = true
                with(binding.repositoriesListView) {
                    showEmptyText(false)
                    showTopProgressBar(true)
                }
            }
        }
    }

    fun onSortButtonClick(v: View) {
        if (!sortDisabled) {
            sortBottomSheetFragment.arguments = Bundle().apply {
                sortBottomSheetFragment.show(childFragmentManager, tag)
            }
        }
    }

    fun orSortDirectionButtonClick(button: View) {
        if (!sortDisabled) {
            sortDisabled = true
            when (sortDirection) {
                SortDirection.ASCENDING -> {
                    sortDirection = SortDirection.DESCENDING
                    (button as MaterialButton).setIconResource(R.drawable.ic_sort_descending)
                }
                SortDirection.DESCENDING -> {
                    sortDirection = SortDirection.ASCENDING
                    (button as MaterialButton).setIconResource(R.drawable.ic_sort_ascending)
                }
            }
            getRepositories()
        }
    }

    fun onBottomSheetButtonClick(sortType: SortType) {
        // closes the bottom sheet fragment
        sortBottomSheetFragment.dismiss()

        sortType.let {
            this.sortType = it
            // Sets the sortTypeButton text depending on the chosen option
            when(it) {
                SortType.ALPHABETIC -> {
                    binding.sortTypeButton.text = getString(R.string.alphabetical)
                }
                SortType.CREATE_DATE -> {
                    binding.sortTypeButton.text = getString(R.string.create_date)
                }
                SortType.PUSH_DATE -> {
                    binding.sortTypeButton.text = getString(R.string.push_date)
                }
                SortType.LAST_UPDATED -> {
                    binding.sortTypeButton.text = getString(R.string.last_updated)
                }
            }
        }

        getRepositories()
    }

    // Clears the previous list and fetches the repositories with chosen parameters
    private fun getRepositories() {
        repositoryListAdapter.clearRepositoryList()
        if (getIsConnectedToInternet()) {
            viewModel.getRepositories(userName, sortType, sortDirection)
        } else {
            with(binding.repositoriesListView) {
                setEmptyText(context.getString(R.string.internet_disconnected))
                showEmptyText(true)
            }
        }
    }


    override fun onInternetConnected() {
        with(binding.repositoriesListView) {
            if (repositoryListAdapter.repositoryList.isNotEmpty()) {
                showEmptyText(false)
            }
        }
        if (previouslyDisconnected) {
            getRepositories()
        }
        super.onInternetConnected()
    }
}