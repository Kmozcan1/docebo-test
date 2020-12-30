package com.kmozcan1.docebotest.presentation.viewstate


/**
 * Created by Kadir Mert Özcan on 29-Dec-20.
 */
data class HomeViewState (
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val userName: String? = null,
    val hasUserProfile: Boolean = false
) {
    companion object {
        fun onSuccess() : HomeViewState = HomeViewState(
                hasError = false,
                isLoading = false,
                isSuccess = true
        )

        fun onError(e: Throwable): HomeViewState = HomeViewState(
                hasError = true,
                errorMessage = e.message
        )
    }


}