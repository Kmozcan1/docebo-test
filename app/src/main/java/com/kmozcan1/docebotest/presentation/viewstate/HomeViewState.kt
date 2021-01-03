package com.kmozcan1.docebotest.presentation.viewstate

import com.kmozcan1.docebotest.domain.model.UserListItem
import com.kmozcan1.docebotest.domain.model.UserSearchResult


/**
 * Created by Kadir Mert Özcan on 29-Dec-20.
 */
data class HomeViewState (
    val state: State,
    val errorMessage: String? = null,
    val userSearchResult: UserSearchResult? = null
) {
    companion object {
        fun error(e: Throwable): HomeViewState = HomeViewState(
            state = State.ERROR,
            errorMessage = e.message
        )

        fun loading(): HomeViewState = HomeViewState(
            state = State.LOADING
        )

        fun userSearchResult(searchResult: UserSearchResult): HomeViewState = HomeViewState(
            state = State.SEARCH_RESULT,
            userSearchResult = searchResult
        )
    }

    enum class State {
        ERROR,
        LOADING,
        SEARCH_RESULT
    }
}