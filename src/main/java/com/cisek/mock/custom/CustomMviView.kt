package com.cisek.mock.custom

import com.cisek.mock.base.MviView
import com.cisek.mock.custom.CustomViewState
import io.reactivex.Observable

interface CustomMviView : MviView {
    fun onStartClick(): Observable<Any>
    fun onPauseClick(): Observable<Any>
    fun onStopClick(): Observable<Any>
    fun render(viewState: CustomViewState)
}