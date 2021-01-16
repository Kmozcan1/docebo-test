package com.kmozcan1.docebotest.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.kmozcan1.docebotest.domain.enums.SortDirection
import com.kmozcan1.docebotest.domain.enums.SortType
import com.kmozcan1.docebotest.ui.viewstate.RepositoriesViewState
import com.kmozcan1.docebotest.usecase.GetUserRepositoriesUseCase

class RepositoriesViewModel @ViewModelInject constructor(
    private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase
): BaseViewModel<RepositoriesViewState>() {

    // Starts observing the GetRepositories observable.
    // This is called when the RepositoriesFragment is created, or when the repositories are sorted
    fun getRepositories(userName: String, sort: SortType = SortType.ALPHABETIC,
                        sortDirection: SortDirection = SortDirection.ASCENDING) {
        setViewState(RepositoriesViewState.loading())
        // Disposes of the observable on new fetch (when sort type or direction is changed)
        getUserRepositoriesUseCase.dispose()
        getUserRepositoriesUseCase.execute(
            params = GetUserRepositoriesUseCase.Params(userName, sort, sortDirection),
            // Gets repositories after subscription
            onSubscribe = {
                getUserRepositoriesUseCase.getRepositories()
            },
            // Updates ViewState on result
            onNext = { repositoriesResult ->
                setViewState(RepositoriesViewState.repositoriesResult(repositoriesResult))
            },
            onError = {
                onError(it)
            }
        )
    }

    fun loadMoreResults() {
        getUserRepositoriesUseCase.loadMore()
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        setViewState(RepositoriesViewState.error(t))
    }
}