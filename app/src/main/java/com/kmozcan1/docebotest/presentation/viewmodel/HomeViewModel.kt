package com.kmozcan1.docebotest.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.kmozcan1.docebotest.domain.interactor.SearchUserUseCase
import com.kmozcan1.docebotest.presentation.viewstate.HomeViewState

class HomeViewModel @ViewModelInject constructor(
        private val searchUserUseCase: SearchUserUseCase
) : BaseViewModel<HomeViewState>() {

    // Calls
    fun searchUser(userName: String, page: Int = 1) {
        setViewState(HomeViewState.loading())
        searchUserUseCase.execute(
                params = SearchUserUseCase.Params(userName, page),
                onSuccess = { userList ->
                    setViewState(HomeViewState.userSearchResult(userList))
                },
                onError = {
                    onError(it)
                }
        )

    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        setViewState(HomeViewState.error(t))
    }
}