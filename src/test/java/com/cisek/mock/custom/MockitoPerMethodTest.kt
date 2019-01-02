package com.cisek.mock.custom

import com.cisek.mock.base.Interactor
import com.cisek.mock.base.Navigator
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.subjects.PublishSubject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MockitoPerMethodTest {

    private val dataSubject = PublishSubject.create<CustomPartialState>()

    private val navigator: Navigator<CustomNavigationTypes> = mock()
    private val interactor: Interactor<CustomPartialState> = mock {
        on { partialStateObserver() } doReturn dataSubject
    }

    private val presenter = CustomPresenter(interactor, navigator)
    private val robot: CustomViewRobot = CustomViewRobot(presenter)

    @Test
    fun `when user click on the start then navigate to the start screen`() {
        robot.startAction()
        verify(navigator).navigate(CustomNavigationTypes.START)
    }

    @Test
    fun `when user click on the pause then navigate to the pause screen`() {
        robot.pauseAction()
        verify(navigator).navigate(CustomNavigationTypes.PAUSE)
    }

    @Test
    fun `when user click on the stop then navigate to the stop screen`() {
        robot.stopAction()
        verify(navigator).navigate(CustomNavigationTypes.STOP)
    }

    @Test
    fun `when app received error then show proper states`() {
        val customError = "custom error"
        dataSubject.onNext(CustomPartialState.Loading)
        dataSubject.onNext(CustomPartialState.Error(customError))

        robot.assertViewStatesRendered(
                CustomViewState(false, emptyList(), ""),
                CustomViewState(true, emptyList(), ""),
                CustomViewState(false, emptyList(), customError)
        )
    }

    @Test
    fun `when app received data then show proper states`() {
        val data = listOf("abc", "123")
        dataSubject.onNext(CustomPartialState.Loading)
        dataSubject.onNext(CustomPartialState.Data(data))

        robot.assertViewStatesRendered(
                CustomViewState(false, emptyList(), ""),
                CustomViewState(true, emptyList(), ""),
                CustomViewState(false, data, "")
        )
    }
}