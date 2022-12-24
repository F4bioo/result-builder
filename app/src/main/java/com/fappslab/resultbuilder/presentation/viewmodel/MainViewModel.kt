package com.fappslab.resultbuilder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fappslab.resultbuilder.arch.resultbuilder.launchIn
import com.fappslab.resultbuilder.arch.resultbuilder.onCompletion
import com.fappslab.resultbuilder.arch.resultbuilder.onFailure
import com.fappslab.resultbuilder.arch.resultbuilder.onStart
import com.fappslab.resultbuilder.arch.resultbuilder.onSuccess
import com.fappslab.resultbuilder.arch.resultbuilder.runAsyncSafely
import com.fappslab.resultbuilder.domain.model.Address
import com.fappslab.resultbuilder.domain.usecase.GetAddressUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAddressUseCase: GetAddressUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState())
    val state = _state.asStateFlow()

    private val _action = MutableSharedFlow<MainViewAction>()
    val action = _action.asSharedFlow()

    fun getAddress(code: String) {
        runAsyncSafely {
            getAddressUseCase(code)
        }.onStart {
            _state.update { it.copy(shouldShowLoading = true) }
        }.onCompletion {
            _state.update { it.copy(shouldShowLoading = false) }
        }.onFailure { cause ->
            getAddressFailure(cause)
        }.onSuccess { address ->
            getAddressSuccess(address)
        }.launchIn(viewModelScope)
    }

    private fun getAddressFailure(cause: Throwable) {
        viewModelScope.launch {
            _action.emit(MainViewAction.ErrorToast(cause.message))
        }
    }

    private fun getAddressSuccess(address: Address) {
        _state.update { it.copy(address = address) }
    }
}
