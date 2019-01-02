package com.cisek.mock.custom

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.jupiter.api.Assertions.assertEquals

class CustomViewRobot(presenter: CustomPresenter) {

    private val renderedStates = arrayListOf<CustomViewState>()

    private val startSubject = PublishSubject.create<Any>()
    private val pauseSubject = PublishSubject.create<Any>()
    private val stopSubject = PublishSubject.create<Any>()

    private val view: CustomMviView = object : CustomMviView {
        override fun onStartClick(): Observable<Any> = startSubject

        override fun onPauseClick(): Observable<Any> = pauseSubject

        override fun onStopClick(): Observable<Any> = stopSubject

        override fun render(viewState: CustomViewState) {
            renderedStates.add(viewState)
        }
    }

    init {
        presenter.bindIntents(view)
    }

    fun assertViewStatesRendered(vararg expectedStates: CustomViewState) {
        assertEquals(expectedStates.toCollection(arrayListOf()), renderedStates)
    }

    fun startAction() {
        startSubject.onNext(true)
    }

    fun pauseAction() {
        pauseSubject.onNext(true)
    }

    fun stopAction() {
        stopSubject.onNext(true)
    }
}