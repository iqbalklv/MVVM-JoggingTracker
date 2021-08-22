package com.miqbalkalevi.joggingtracker.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miqbalkalevi.joggingtracker.data.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    var height: Int? = null
    var weight: Int? = null
    val inputCount = MutableLiveData<List<Int>>()
    private var _inputCount = mutableListOf<Int>()

    private val LoginEventChannel = Channel<LoginEvent>()
    val loginEvent = LoginEventChannel.receiveAsFlow()

    init {
        _inputCount = mutableListOf(0, 0)
        inputCount.value = mutableListOf(0, 0)
    }

    fun onEditTextChanged(index: Int, length: Int) {
        _inputCount[index] = length
        inputCount.value = _inputCount
    }

    fun isButtonValidated(): Boolean {
        var point = 0
        inputCount.value?.forEach {
            if (it > 0) {
                point++
            }
        }

        return point == inputCount.value?.size
    }

    fun onStartButtonClick() = viewModelScope.launch {
        preferenceManager.updateUserData(weight!!, height!!)
        LoginEventChannel.send(LoginEvent.NavigateToMainScreen)
    }

    sealed class LoginEvent {
        object NavigateToMainScreen : LoginEvent()
    }


}