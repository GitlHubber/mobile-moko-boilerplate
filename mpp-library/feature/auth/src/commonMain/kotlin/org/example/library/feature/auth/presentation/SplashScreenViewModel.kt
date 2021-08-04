package org.example.library.feature.auth.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class SplashScreenViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel(), EventsDispatcherOwner<SplashScreenViewModel.EventsListener> {

    fun checkAuth() {
        // TODO: Произвести проверку авторизованности, вызвать соответствующий переход
    }

    interface EventsListener {
        fun routeToMain()
        fun routeToAuth()
    }
}