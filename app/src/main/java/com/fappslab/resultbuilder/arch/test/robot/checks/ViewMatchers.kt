package com.fappslab.resultbuilder.arch.test.robot.checks

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers


fun setText(@IdRes resId: Int, text: String) {
    Espresso.onView(ViewMatchers.withId(resId))
        .perform(ViewActions.typeText(text))
}
