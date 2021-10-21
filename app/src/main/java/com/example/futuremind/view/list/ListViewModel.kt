package com.example.futuremind.view.list

import androidx.lifecycle.*
import com.example.futuremind.repository.RequestState
import com.example.futuremind.view.error.ErrorState
import com.example.futuremind.model.Item
import com.example.futuremind.repository.ItemRepository
import com.example.futuremind.view.SingleLiveEvent
import com.example.futuremind.view.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class ListViewModel(
    private val itemRepository: ItemRepository,
    savedState: SavedStateHandle,
    mainDispatcher: CoroutineDispatcher
) : BaseViewModel(mainDispatcher) {

    private var refreshJob: Job? = null

    private val _items: MutableLiveData<List<Item>> = savedState.getLiveData("items")
    val items: LiveData<List<Item>> = _items

    private val _fullScreenErrorState: MutableLiveData<ErrorState> = savedState.getLiveData("error", ErrorState.NO_ERROR)
    val fullScreenErrorState: LiveData<ErrorState> = _fullScreenErrorState

    private val _showProgress: MutableLiveData<Boolean> = MutableLiveData()
    val showProgress: LiveData<Boolean> = _showProgress

    private val _snackBarErrorEvent = SingleLiveEvent<ErrorState>()
    val snackBarErrorEvent = _snackBarErrorEvent

    fun refresh() {
        refreshJob?.cancel()
        refreshJob = itemRepository.getItems()
            .onStart {
                _showProgress.value = true
            }
            .onCompletion {
                _showProgress.value = false
            }
            .onEach {
                when (it) {
                    is RequestState.Success -> {
                        _items.value = it.data
                    }
                    is RequestState.Loading -> {
                        _items.value = it.data
                    }
                    else -> {
                    }
                }
                changeErrorState(it)
            }.launchIn(viewModelScope)
    }

    private fun changeErrorState(requestState: RequestState<List<Item>>) {
        requestState.let {
            val emptyList = _items.value.isNullOrEmpty()
            when {
                it is RequestState.Error && it.isNetworkError() && emptyList -> _fullScreenErrorState.value =
                    ErrorState.NETWORK_ERROR
                it is RequestState.Error && it.isDataError() && emptyList -> _fullScreenErrorState.value =
                    ErrorState.DATA_ERROR
                it is RequestState.Error && it.isNetworkError() && !emptyList -> {
                    snackBarErrorEvent.value = ErrorState.NETWORK_ERROR
                    _fullScreenErrorState.value = ErrorState.NO_ERROR
                }
                it is RequestState.Error && it.isDataError() && !emptyList -> {
                    snackBarErrorEvent.value = ErrorState.DATA_ERROR
                    _fullScreenErrorState.value = ErrorState.NO_ERROR
                }
                else -> _fullScreenErrorState.value = ErrorState.NO_ERROR
            }
        }
    }
}