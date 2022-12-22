package com.fappslab.resultbuilder.presentation.viewmodel

import app.cash.turbine.test
import com.fappslab.resultbuilder.data.source.GENERIC_ERROR_MESSAGE
import com.fappslab.resultbuilder.domain.usecase.GetAddressUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import stubs.stubAddress
import utils.DispatcherTestRule
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    private val initialState = MainViewState()
    private val address = stubAddress()

    private val getAddressUseCase: GetAddressUseCase = mockk()
    private lateinit var subject: MainViewModel

    @Before
    fun setUp() {
        subject = MainViewModel(
            getAddressUseCase = getAddressUseCase
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getAddressSuccess Should expose correct states When getAddress return success result`() {
        // Given
        val expectedFirstState = initialState.copy(shouldShowLoading = true)
        val expectedSecondState = expectedFirstState.copy(shouldShowLoading = false)
        val expectedFinalState = expectedSecondState.copy(address = address)
        coEvery { getAddressUseCase(any()) } returns address

        // When
        subject.getAddress(code = "01001-000")

        // Then
        runTest {
            subject.state.test {
                assertEquals(initialState, awaitItem())
                assertEquals(expectedFirstState, awaitItem())
                assertEquals(expectedSecondState, awaitItem())
                assertEquals(expectedFinalState, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
        coVerify { getAddressUseCase(any()) }
    }

    @Test
    fun `getAddressFailure Should expose ErrorToast action When getAddress return failure result`() {
        // Given
        val throwable = Throwable(GENERIC_ERROR_MESSAGE)
        val expectedAction = MainViewAction.ErrorToast(throwable.message)
        coEvery { getAddressUseCase(any()) } throws throwable

        // When
        subject.getAddress(code = "01001-000")

        // Then
        runTest {
            subject.action.test {
                val result = awaitItem()
                assertEquals(expectedAction, result)
                assertEquals(throwable.message, (result as MainViewAction.ErrorToast).message)
                cancelAndConsumeRemainingEvents()
            }
        }
        coVerify { getAddressUseCase(any()) }
    }
}
