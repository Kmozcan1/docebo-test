package com.kmozcan1.docebotest.presentation.viewstate

import com.kmozcan1.docebotest.domain.model.RepositoriesResultModel
import com.kmozcan1.docebotest.domain.model.UserSearchResultModel

/**
 * Created by Kadir Mert Özcan on 05-Jan-21.
 */
data class RepositoriesViewState(
    val state: State,
    val errorMessage: String? = null,
    val repositoriesResult: RepositoriesResultModel? = null) {

    companion object {
        fun error(e: Throwable): RepositoriesViewState = RepositoriesViewState(
            state = State.ERROR,
            errorMessage = e.message
        )

        fun loading(): RepositoriesViewState = RepositoriesViewState(
            state = State.LOADING
        )

        fun repositoriesResult(repositoriesResult: RepositoriesResultModel):
                RepositoriesViewState = RepositoriesViewState(
            state = State.REPOSITORY_RESULT,
            repositoriesResult = repositoriesResult
        )
    }

    enum class State {
        ERROR,
        LOADING,
        REPOSITORY_RESULT
    }
}
