package com.kmozcan1.docebotest.domain.interactor.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Kadir Mert Özcan on 29-Dec-20.
 *
 * Base class for interactors that return [Flowable] objects
 */
abstract class FlowableUseCase<Result, in Params> : Disposable {
    private lateinit var disposable: Disposable

    /**
     * Abstract function where the interactor class calls methods that handle domain logic
     */
    abstract fun buildObservable(params: Params?): Flowable<Result>

    /**
     * Builds and subscribes the [Flowable] object, then adds it to the list of disposables
     */
    fun execute(params: Params?,
                onComplete: () -> Unit = {},
                onNext: Consumer<Result>? = Consumer {  },
                onError: Consumer<Throwable>? = Consumer {  }) {
        disposable = buildObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(onNext)
            .doOnComplete(onComplete)
            .doOnError(onError)
            .subscribe({}, Timber::w)
    }

    override fun dispose() {
        return disposable.dispose()
    }

    override fun isDisposed(): Boolean {
        return disposable.isDisposed
    }
}