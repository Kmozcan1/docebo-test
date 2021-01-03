package com.kmozcan1.docebotest.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.kmozcan1.docebotest.domain.interactor.SearchUserUseCase
import com.kmozcan1.docebotest.presentation.viewstate.HomeViewState

class HomeViewModel @ViewModelInject constructor(
        private val searchUserUseCase: SearchUserUseCase
) : BaseViewModel<HomeViewState>() {

    // Starts observing the SearchUseCase observable. This is called when the user makes a new search
    fun searchUser(userName: String) {
        setViewState(HomeViewState.loading())
        searchUserUseCase.dispose()
        searchUserUseCase.execute(
            params = SearchUserUseCase.Params(userName),
            onSubscribe = {
                searchUserUseCase.searchUser()
            },
            onNext = { searchResult ->
                setViewState(HomeViewState.userSearchResult(searchResult))
            },
            onError = {
                onError(it)
            },
        )
    }

    fun loadMoreResults() {
        searchUserUseCase.loadMore()
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        setViewState(HomeViewState.error(t))
    }
}