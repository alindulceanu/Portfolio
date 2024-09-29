package com.example.portfolio.viewmodels.templates

import android.util.Log.d
import androidx.lifecycle.ViewModel
import com.example.portfolio.viewmodels.events.Events

abstract class ViewModelTemplate<in T : Events> : ViewModel() {

    protected open fun initState() {
        d("viewmodel", "I did nothing!")
    }

    abstract fun onEvent(event: T)
}