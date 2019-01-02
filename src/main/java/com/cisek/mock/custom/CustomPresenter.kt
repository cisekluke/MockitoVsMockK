package com.cisek.mock.custom

import com.cisek.mock.base.Interactor
import com.cisek.mock.base.Navigator
import com.cisek.mock.custom.CustomPartialStateReducer.Companion.reduceState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CustomPresenter @Inject constructor(private val interactor: Interactor<CustomPartialState>,
                                          private val navigator: Navigator<CustomNavigationTypes>) {

    private val compositeDisposable = CompositeDisposable()

    fun bindIntents(view: CustomMviView) {
        compositeDisposable.add(view.onStartClick().subscribe({
            navigator.navigate(CustomNavigationTypes.START)
        }, {}))

        compositeDisposable.add(view.onPauseClick().subscribe({
            navigator.navigate(CustomNavigationTypes.PAUSE)
        }, {}))

        compositeDisposable.add(view.onStopClick().subscribe({
            navigator.navigate(CustomNavigationTypes.STOP)
        }, {}))

        val data = interactor.partialStateObserver()

        compositeDisposable.add(data.scan(CustomViewState(), ::reduceState)
                .subscribe({ view.render(it) }, {}))
    }

    fun unbindIntents() {
        compositeDisposable.clear()
    }
}