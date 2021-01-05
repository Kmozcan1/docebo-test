package com.kmozcan1.docebotest.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.RepositoriesFragmentBinding
import com.kmozcan1.docebotest.presentation.ArgConstants
import com.kmozcan1.docebotest.presentation.adapter.RepositoryListAdapter
import com.kmozcan1.docebotest.presentation.adapter.UserListAdapter
import com.kmozcan1.docebotest.presentation.viewmodel.RepositoriesViewModel
import com.kmozcan1.docebotest.presentation.viewstate.ProfileViewState
import com.kmozcan1.docebotest.presentation.viewstate.RepositoriesViewState
import com.kmozcan1.docebotest.presentation.viewstate.RepositoriesViewState.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesFragment : BaseFragment<RepositoriesFragmentBinding, RepositoriesViewModel>() {

    companion object {
        fun newInstance() = RepositoriesFragment()
    }

    private lateinit var userName: String

    private val repositoryListCallbackListener = repositoryListCallbackListener()

    // RecyclerView Adapter
    private val repositoryListAdapter: RepositoryListAdapter by lazy {
        RepositoryListAdapter(mutableListOf(), repositoryListCallbackListener)
    }

    override fun layoutId() = R.layout.repositories_fragment

    override fun getViewModelClass(): Class<RepositoriesViewModel> =
        RepositoriesViewModel::class.java

    override fun onViewBound() {
        binding.repositoriesFragment = this

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
        viewModel.getRepositories(userName)
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
        activity?.supportFragmentManager?.let {
            val fragment = SortBottomSheetFragment()
            fragment.arguments = Bundle().apply {
                fragment.show(it, tag)
            }
        }
    }
}