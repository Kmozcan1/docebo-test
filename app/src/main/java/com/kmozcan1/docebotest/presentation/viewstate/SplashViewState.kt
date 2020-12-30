package com.kmozcan1.docebotest.presentation.viewstate

/**
 * Created by Kadir Mert Özcan on 29-Dec-20.
 */
data class SplashViewState (
        val isLoading: Boolean = false,
        val hasError: Boolean = false,
        val errorMessage: String? = null,
        val isSuccess: Boolean = false,
        val isLoggedIn: Boolean = false
) {
    companion object {
        fun onLoading() : SplashViewState = SplashViewState(
                isLoading = true,
                isSuccess = false
        )

        fun onSuccess() : SplashViewState = SplashViewState(
                hasError = false,
                isLoading = false,
                isSuccess = true
        )

        fun onError(e: Throwable): SplashViewState = SplashViewState(
                hasError = true,
                errorMessage = e.message,
                isSuccess = false
        )

        fun onLoggedIn(isLoggedIn: Boolean) : SplashViewState = SplashViewState(
                isSuccess = true,
                isLoggedIn = isLoggedIn
        )
    }

}