package com.fappslab.resultbuilder.presentation

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import com.fappslab.resultbuilder.arch.test.robot.RobotActivity
import com.fappslab.resultbuilder.presentation.viewmodel.MainViewAction
import com.fappslab.resultbuilder.presentation.viewmodel.MainViewModel
import com.fappslab.resultbuilder.presentation.viewmodel.MainViewState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

private typealias MainActivityRobotAlias = RobotActivity<MainActivity, MainActivityRobotCheck,
        MainViewState, MainViewAction, MainViewModel>

class MainActivityRobot : MainActivityRobotAlias, TestWatcher() {

    private lateinit var scenario: ActivityScenario<MainActivity>

    private val fakeState = MutableStateFlow(MainViewState())
    private val fakeAction = MutableSharedFlow<MainViewAction>()
    private val fakeViewModel = mockk<MainViewModel>(relaxed = true) {
        every { state } returns fakeState
        every { action } returns fakeAction
    }

    private fun setupModules() = module {
        viewModel { fakeViewModel }
    }

    override fun starting(description: Description) {
        super.starting(description)
        stopKoin()
        startKoin { loadKoinModules(setupModules()) }
    }

    override fun finished(description: Description) {
        super.finished(description)
        scenario.close()
        stopKoin()
    }

    override fun givenState(
        state: () -> MainViewState,
    ): MainActivityRobotAlias {
        fakeState.update { state() }
        return this
    }

    override fun givenAction(
        invoke: MainViewModel.() -> Unit,
        action: () -> MainViewAction,
    ): MainActivityRobotAlias {
        every {
            invoke(fakeViewModel)
        } coAnswers {
            fakeAction.emit(action())
        }
        return this
    }

    override fun whenLaunch(
        activity: (MainActivity) -> Unit,
    ): MainActivityRobotCheck {
        scenario = launchActivity<MainActivity>()
            .onActivity(activity)
        return MainActivityRobotCheck()
    }
}
