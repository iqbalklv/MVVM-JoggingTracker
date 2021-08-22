package com.miqbalkalevi.joggingtracker.ui.runs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miqbalkalevi.joggingtracker.data.JogSortOrder
import com.miqbalkalevi.joggingtracker.data.PreferenceManager
import com.miqbalkalevi.joggingtracker.data.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    val preferencesFlow = preferenceManager.preferencesFlow

    val runs = preferencesFlow.flatMapLatest { filterPreferences ->
        mainRepository.getJogsSortedBy(filterPreferences.sortOrder)
    }

    fun onSortOrderSelected(sortOrder: JogSortOrder) = viewModelScope.launch {
        preferenceManager.updateSortOrder(sortOrder)
    }
}