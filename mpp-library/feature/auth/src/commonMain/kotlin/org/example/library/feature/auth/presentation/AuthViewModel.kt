/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library.feature.auth.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch
import org.example.library.feature.auth.di.AuthRepository

class AuthViewModel(
    private val authRepository: AuthRepository,
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
) : ViewModel(), EventsDispatcherOwner<AuthViewModel.EventsListener> {

    val loginField: MutableLiveData<String> = MutableLiveData<String>("")
    val passwordField: MutableLiveData<String> = MutableLiveData<String>("")

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading.readOnly()

    fun onLoginTap() {
        println("Button tapped!")
        viewModelScope.launch {
            try {
                sendAuthRequest()
                eventsDispatcher.dispatchEvent {
                    routeToMain()
                }
            } catch (exception: Exception) {
                eventsDispatcher.dispatchEvent {
                    val errorMessage = exception.message ?: "Ошибка авторизации"
                    showError(errorText = errorMessage.desc())
                }
            }
        }
    }

    private suspend fun sendAuthRequest() {
        authRepository.auth(login = loginField.value, password = passwordField.value)
    }

    interface EventsListener {
        fun routeToMain()
        fun showError(errorText: StringDesc)
    }
}
