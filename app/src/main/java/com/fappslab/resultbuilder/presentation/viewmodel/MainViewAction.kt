package com.fappslab.resultbuilder.presentation.viewmodel

sealed class MainViewAction {
    data class ErrorToast(val message: String?) : MainViewAction()
}
