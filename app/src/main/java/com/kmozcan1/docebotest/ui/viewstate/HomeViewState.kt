package com.kmozcan1.docebotest.ui.viewstate

import com.kmozcan1.docebotest.domain.model.UserSearchItemModel
import com.kmozcan1.docebotest.domain.model.UserSearchResultModel


/**
 * Created by Kadir Mert Özcan on 29-Dec-20.
 */
data class HomeViewState (
        val state: State,
        val errorMessage: String? = null,
        val userSearchResult: UserSearchResultModel? = null,
        val allSearchResults: MutableList<UserSearchItemModel>? = null,
        val page: Int? = null,
        val searchQuery: String? = null
) {
    companion object {
        fun initial(): HomeViewState = HomeViewState(
            state = State.INITIAL
        )

        fun error(e: Throwable): HomeViewState = HomeViewState(
            state = State.ERROR,
            errorMessage = e.message
        )

        fun loading(): HomeViewState = HomeViewState(
            state = State.LOADING
        )

        fun userSearchResult(searchResult: UserSearchResultModel,
                             allSearchResults: MutableList<UserSearchItemModel>): HomeViewState = HomeViewState(
            state = State.SEARCH_RESULT,
            userSearchResult = searchResult,
            allSearchResults = allSearchResults
        )
    }

    enum class State {
        ERROR,
        INITIAL,
        LOADING,
        SEARCH_RESULT
    }
}