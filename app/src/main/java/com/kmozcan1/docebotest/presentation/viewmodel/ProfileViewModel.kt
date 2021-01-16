package com.kmozcan1.docebotest.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.kmozcan1.docebotest.ui.viewstate.ProfileViewState
import com.kmozcan1.docebotest.usecase.GetUserUseCase


class ProfileViewModel @ViewModelInject constructor(
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel<ProfileViewState>() {

    fun getUserProfile(userName: String) {
        setViewState(ProfileViewState.loading())
        getUserUseCase.execute(
            GetUserUseCase.Params(userName),
            onSuccess = { userProfile ->
                setViewState(ProfileViewState.userProfileResult(userProfile))
            },
            onError = {
                onError(it)
            }
        )
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        setViewState(ProfileViewState.error(t))
    }
}