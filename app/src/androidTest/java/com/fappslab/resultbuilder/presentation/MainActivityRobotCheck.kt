package com.fappslab.resultbuilder.presentation

import androidx.annotation.IdRes
import com.fappslab.resultbuilder.R
import com.fappslab.resultbuilder.arch.test.robot.RobotCheck
import com.fappslab.resultbuilder.arch.test.robot.checks.checkButtonClicked
import com.fappslab.resultbuilder.arch.test.robot.checks.checkInputTextHasText
import com.fappslab.resultbuilder.arch.test.robot.checks.checkIsDisplayed
import com.fappslab.resultbuilder.arch.test.robot.checks.checkIsNotDisplayed
import com.fappslab.resultbuilder.arch.test.robot.checks.checkSnackBarIsHasText

class MainActivityRobotCheck : RobotCheck<MainActivityRobotCheck> {

    fun checkLoadingIsDisplayed() {
        checkIsDisplayed(R.id.loading)
    }

    fun checkLoadingIsNotDisplayed() {
        checkIsNotDisplayed(R.id.loading)
    }

    fun checkSearchButtonIsClicked() {
        checkButtonClicked(R.id.button_search)
    }

    fun checkSnackBarErrorIsDisplayed(text: String) {
        checkSnackBarIsHasText(text)
    }

    fun checkInputTextHasExactlyText(@IdRes resId: Int, text: String) {
        checkInputTextHasText(resId, text)
    }
}
