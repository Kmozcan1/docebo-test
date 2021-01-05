package com.kmozcan1.docebotest.presentation.ui

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

    private val repositoryListCallbackListener = repositoryListCallbackListener()

    // RecyclerView Adapter
    private val repositoryListAdapter: RepositoryListAdapter by lazy {
        RepositoryListAdapter(mutableListOf(), repositoryListCallbackListener)
    }

    private val sortBottomSheetFragment: RepoSortBottomSheetFragment by lazy {
        RepoSortBottomSheetFragment()
    }

    override fun layoutId() = R.layout.repositories_fragment

    override fun getViewModelClass(): Class<RepositoriesViewModel> =
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

    override fun observeLiveDate() {
        // Observes the ViewState
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
        getRepositories()
    }

    // Returns paginated list callback listener object
    private fun repositoryListCallbackListener() = object : PaginatedListView.CallbackListener {
        // Loads more results when the final item has been reached
        // (only invoked if finalPage = false)
        override fun onPaginatedListFinalItemVisible() {
            binding.repositoriesListView.showProgressBar(true)
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
                    // Hides the progress bar at the end of the paginated list
                    showProgressBar(false)
                    // Adds results
                    viewState.repositoriesResult?.let { repositoriesResult ->
                        repositoryListAdapter.addRepositories(repositoriesResult.repositoryList)
                        onFinalPage = repositoriesResult.finalPage
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

    fun onSortButtonClick(v: View) {
        sortBottomSheetFragment.arguments = Bundle().apply {
            sortBottomSheetFragment.show(childFragmentManager, tag)
        }
    }

    fun orSortDirectionButtonClick(button: View) {
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
        viewModel.getRepositories(userName, sortType, sortDirection)
    }
}