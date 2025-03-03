package com.maxsch.symmetricguide.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun Disposable.untilDestroy() = compositeDisposable.add(this)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}