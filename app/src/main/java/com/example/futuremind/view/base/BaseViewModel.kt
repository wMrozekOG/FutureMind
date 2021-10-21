package com.example.futuremind.view.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    protected val mainDispatcher: CoroutineDispatcher
): ViewModel() {

    /**
     * Launches given 'block' using viewModelScope
     *
     * @param block to be executed in viewModelScope
     */
    fun launch(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(mainDispatcher, block = block)
}