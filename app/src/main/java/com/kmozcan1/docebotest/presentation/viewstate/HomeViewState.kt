package com.kmozcan1.docebotest.presentation.viewstate

import com.kmozcan1.docebotest.domain.model.UserSearchListModel


/**
 * Created by Kadir Mert Özcan on 29-Dec-20.
 */
data class HomeViewState (
        val state: State,
        val errorMessage: String? = null,
        val userSearchResult: List<UserSearchListModel> = mutableListOf()
) {
    companion object {
        fun error(e: Throwable): HomeViewState = HomeViewState(
                state = State.ERROR,
                errorMessage = e.message
        )

        fun loading(): HomeViewState = HomeViewState(
                state = State.LOADING
        )

        fun userSearchResult(userList: List<UserSearchListModel>): HomeViewState = HomeViewState(
                state = State.SEARCH_RESULT,
                userSearchResult = userList
        )
    }

    enum class State {
        ERROR,
        LOADING,
        SEARCH_RESULT
    }
}