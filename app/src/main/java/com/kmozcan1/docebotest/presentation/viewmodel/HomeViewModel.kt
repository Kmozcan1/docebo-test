package com.kmozcan1.docebotest.presentation.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.kmozcan1.docebotest.domain.model.UserSearchItemModel
import com.kmozcan1.docebotest.ui.viewstate.HomeViewState
import com.kmozcan1.docebotest.usecase.SearchUserUseCase

class HomeViewModel @ViewModelInject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeViewState>() {

    private val allSearchResults = mutableListOf<UserSearchItemModel>()
    var hasRetainedList: Boolean = false

    init {
        setViewState(HomeViewState.initial())
    }

    // Starts observing the SearchUseCase observable. This is called when the user makes a new search
    fun searchUser(userName: String = "") {
        setViewState(HomeViewState.loading())
        // Disposes of the observable on new search
        searchUserUseCase.dispose()
        searchUserUseCase.execute(
            params = SearchUserUseCase.Params(userName),
            // Searches for the users after subscription
            onSubscribe = {
                searchUserUseCase.searchUser()
                allSearchResults.clear()
            },
            // Updates ViewState on result
            onNext = { searchResult ->
                allSearchResults.addAll(searchResult.userList)
                setViewState(HomeViewState.userSearchResult(searchResult, allSearchResults))
            },
            onError = {
                onError(it)
            }
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