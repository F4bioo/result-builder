package com.fappslab.resultbuilder.presentation.viewmodel

import com.fappslab.resultbuilder.arch.viewmodel.State
import com.fappslab.resultbuilder.domain.model.Address

data class MainViewState(
    val shouldShowLoading: Boolean = false,
    val address: Address? = null,
) : State
