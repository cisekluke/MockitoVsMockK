package com.cisek.mock.base

import io.reactivex.Observable

interface Interactor<T> {
    fun partialStateObserver(): Observable<T>
}