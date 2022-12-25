package com.fappslab.resultbuilder.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fappslab.resultbuilder.R
import com.fappslab.resultbuilder.presentation.viewmodel.MainViewAction
import com.fappslab.resultbuilder.presentation.viewmodel.MainViewState
import com.fappslab.resultbuilder.stubs.stubAddress
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val robot = MainActivityRobot()

    private val initialState = MainViewState()

    @Test
    fun checkLoadingIsDisplayed_Should_display_loading_When_shouldShowLoading_state_is_true() {
        val expectedState = initialState.copy(shouldShowLoading = true)

        robot.givenState { expectedState }
            .whenLaunch()
            .thenCheck {
                checkLoadingIsDisplayed()
            }
    }

    @Test
    fun checkLoadingIsNotDisplayed_Should_hide_loading_When_shouldShowLoading_state_is_false() {
        val expectedState = initialState.copy(shouldShowLoading = false)

        robot.givenState { expectedState }
            .whenLaunch()
            .thenCheck {
                checkLoadingIsNotDisplayed()
            }
    }

    @Test
    fun checkInputTexts_Should_inputs_get_address_When_invoke_searchButtonClick_with_valid_code() {
        val address = stubAddress()
        val expectedState = initialState.copy(address = address)
        val expectedTexts = mapOf(
            R.id.input_street to address.street,
            R.id.input_city to address.city,
            R.id.input_state to address.state,
            R.id.input_area_code to address.areaCode,
        )

        robot.givenState { expectedState }
            .whenLaunch()
            .thenCheck {
                expectedTexts.forEach {
                    checkInputTextHasExactlyText(it.key, expectedTexts.getValue(it.key))
                }
            }
    }

    @Test
    fun checkErrorMessage_Should_show_error_message_When_invoke_searchButtonClick_with_empty_code() {
        val expectedMessage = "Error message."

        robot.givenAction(
            invoke = { getAddress(code = "") },
            action = { MainViewAction.ErrorMessage(expectedMessage) }
        ).whenLaunch()
            .thenCheck {
                checkSearchButtonIsClicked()
            }.thenCheck {
                checkSnackBarErrorIsDisplayed(expectedMessage)
            }
    }
}
