package com.fappslab.resultbuilder.arch.test.robot

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.fappslab.resultbuilder.arch.viewmodel.Action
import com.fappslab.resultbuilder.arch.viewmodel.State

interface RobotActivity<V : Activity, CB : RobotCheck<CB>, S : State, A : Action, VM : ViewModel> {
    fun givenState(state: () -> S): RobotActivity<V, CB, S, A, VM> = this
    fun givenAction(invoke: VM.() -> Unit, action: () -> A): RobotActivity<V, CB, S, A, VM> = this
    fun whenLaunch(activity: (V) -> Unit = {}): CB
}


interface RobotFragment<V : Fragment, RC : RobotCheck<RC>, S : State, A : Action, VM : ViewModel> {
    fun givenState(state: () -> S): RobotFragment<V, RC, S, A, VM> = this
    fun givenAction(invoke: VM.() -> Unit, action: () -> A): RobotFragment<V, RC, S, A, VM> = this
    fun whenLaunch(fragment: (V) -> Unit = {}): RC
}
