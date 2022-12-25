package com.fappslab.resultbuilder.presentation.viewmodel

import com.fappslab.resultbuilder.arch.viewmodel.Action

sealed class MainViewAction : Action {
    data class ErrorMessage(val message: String?) : MainViewAction()
}
