package com.kmozcan1.docebotest.ui.presentation

import androidx.hilt.lifecycle.ViewModelInject
import com.kmozcan1.docebotest.ui.viewstate.MainViewState
import com.kmozcan1.docebotest.domain.usecase.ObserveInternetConnectionUseCase

/**
 * Created by Kadir Mert Özcan on 29-Dec-20.
 */
class MainViewModel @ViewModelInject constructor(
    private val observeInternetConnectionUseCase: ObserveInternetConnectionUseCase
) : BaseViewModel<MainViewState>() {

    fun observeInternetConnection() {
        observeInternetConnectionUseCase.execute(
            onComplete = {},
            onNext = { isConnected ->
                setViewState(MainViewState.connectionChange(isConnected))
            },
            onError = {
                onError(it)
            },
        )
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        setViewState(MainViewState.error(t))
    }
}