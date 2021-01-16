package com.kmozcan1.docebotest.presentation.viewstate

import com.kmozcan1.docebotest.domain.model.UserProfileModel

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */
data class ProfileViewState (
    val state: State,
    val errorMessage: String? = null,
    val userProfileModel: UserProfileModel? = null)
{

    companion object {
        fun error(e: Throwable): ProfileViewState = ProfileViewState(
            state = State.ERROR,
            errorMessage = e.message
        )

        fun loading(): ProfileViewState = ProfileViewState(
            state = State.LOADING
        )

        fun userProfileResult(userProfile: UserProfileModel): ProfileViewState = ProfileViewState(
            state = State.USER_RESULT,
            userProfileModel = userProfile
        )
    }

    enum class State {
        ERROR,
        LOADING,
        USER_RESULT
    }
}