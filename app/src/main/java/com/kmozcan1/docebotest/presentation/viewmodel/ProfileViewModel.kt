package com.kmozcan1.docebotest.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.kmozcan1.docebotest.domain.interactor.GetUserUseCase
import com.kmozcan1.docebotest.presentation.viewstate.HomeViewState
import com.kmozcan1.docebotest.presentation.viewstate.ProfileViewState
import javax.inject.Inject


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