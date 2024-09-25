package com.example.lern.viewmodels.templates

import android.util.Log.d
import androidx.lifecycle.ViewModel
import com.example.lern.viewmodels.events.Events
import com.example.lern.viewmodels.states.States
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class ViewModelTemplate<T: States, in R: Events> : ViewModel() {

    protected abstract val _uiState: MutableStateFlow<T>
    open val uiState: StateFlow<T>
        get() = _uiState

    protected open fun initState() {
        d("viewmodel", "I did nothing!")
    }

    abstract fun onEvent(event: R)
}