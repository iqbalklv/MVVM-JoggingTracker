package com.miqbalkalevi.joggingtracker.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var height: Int? = null
    var weight: Int? = null
    val inputCount = MutableLiveData<List<Int>>()
    private var _inputCount = mutableListOf<Int>()

    init {
        _inputCount = mutableListOf(0, 0)
        inputCount.value = mutableListOf(0, 0)
    }

    fun onEditTextChanged(index: Int, length: Int) {
        _inputCount[index] = length
        inputCount.value = _inputCount
        Log.d("INPUTCOUNT", inputCount.value.toString())
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


}