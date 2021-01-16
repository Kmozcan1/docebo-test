package com.kmozcan1.docebotest.ui.viewstate

/**
 * Created by Kadir Mert Özcan on 29-Dec-20.
 */
data class MainViewState(
        val state: State,
        val errorMessage: String? = null,
        val isConnected: Boolean = false
) {
    companion object {
        fun error(e: Throwable): MainViewState = MainViewState(
                state = State.ERROR,
                errorMessage = e.message,
        )

        fun connectionChange(isConnected: Boolean): MainViewState = MainViewState(
                state = State.CONNECTION_CHANGE,
                isConnected = isConnected
        )
    }

    enum class State {
        ERROR,
        LOADING,
        CONNECTION_CHANGE
    }
}
