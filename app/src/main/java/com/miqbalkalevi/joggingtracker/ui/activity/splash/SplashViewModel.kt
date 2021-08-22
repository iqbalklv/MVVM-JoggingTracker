package com.miqbalkalevi.joggingtracker.ui.activity.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miqbalkalevi.joggingtracker.data.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val splashEventChannel = Channel<SplashEvent>()
    val splashEvent = splashEventChannel.receiveAsFlow()

    fun onSplashFinished() = viewModelScope.launch {
        val userData = preferenceManager.readUserData()
        if (userData == null) {
            splashEventChannel.send(SplashEvent.NavigateToLandingScreen)
        } else {
            splashEventChannel.send(SplashEvent.NavigateToMainScreen)
        }
    }

    sealed class SplashEvent {
        object NavigateToLandingScreen : SplashEvent()
        object NavigateToMainScreen : SplashEvent()
    }
}