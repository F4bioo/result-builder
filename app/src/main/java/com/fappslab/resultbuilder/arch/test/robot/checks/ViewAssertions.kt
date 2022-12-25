package com.fappslab.resultbuilder.arch.test.robot.checks

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers

fun checkIsDisplayed(@IdRes resId: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

fun checkIsNotDisplayed(@IdRes resId: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
}

fun checkIsEnabled(@IdRes resId: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
}

fun checkIsNotEnabled(@IdRes resId: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()))
}

fun checkInputTextHasText(@IdRes resId: Int, expectedText: String) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.withText(expectedText)))
}

fun checkInputTextIsEmpty(@IdRes resId: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.withText("")))
}

fun checkInputTextHasHint(@IdRes resId: Int, expectedText: String) {
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.withHint(expectedText)))
}

fun checkButtonClicked(@IdRes resId: Int) {
    Espresso.onView(ViewMatchers.withId(resId))
        .perform(ViewActions.click())
}

fun checkSnackBarIsDisplayed() {
    Espresso.onView(ViewMatchers.withId(com.google.android.material.R.id.snackbar_text))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

fun checkSnackBarIsHasText(text: String) {
    Espresso.onView(ViewMatchers.withText(text))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

fun checkSnackBarIsHasText(@StringRes textRes: Int) {
    Espresso.onView(ViewMatchers.withText(textRes))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}
