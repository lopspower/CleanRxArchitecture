package com.mikhaellopez.presentation.extensions

import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers

// ID matcher

fun Int.matchView(): ViewInteraction = Espresso.onView(ViewMatchers.withId(this))

// ID view interaction

fun Int.checkIsDisplayed(displayed: Boolean = true) {
    matchView().checkIsDisplayed(displayed)
}

fun Int.checkWithText(text: String) {
    matchView().checkWithText(text)
}

fun Int.checkChildWithText(text: String) {
    Espresso.onView(ViewMatchers.withText(text))
        .check(ViewAssertions.matches(ViewMatchers.withParent(ViewMatchers.withId(this))))
}

fun Int.checkChildWithText(@StringRes resourceId: Int) {
    Espresso.onView(ViewMatchers.withText(resourceId))
        .check(ViewAssertions.matches(ViewMatchers.withParent(ViewMatchers.withId(this))))
}

fun Int.performScrollRecyclerTo(position: Int) {
    matchView().performScrollRecyclerTo(position)
}

fun <T : RecyclerView.ViewHolder> Int.performActionOnRecyclerItemAtPosition(
    position: Int,
    action: ViewAction
) {
    matchView().performActionOnRecyclerItemAtPosition<T>(position, action)
}

fun Int.performClick() {
    matchView().performClick()
}

fun Int.checkAdapterItemContent(position: Int, text: String) {
    Espresso.onData(
        Matchers.allOf(
            Matchers.`is`(Matchers.instanceOf(String::class.java)),
            Matchers.`is`(text)
        )
    )
        .inAdapterView(ViewMatchers.withId(this))
        .atPosition(position)
}

// ViewInteraction

fun ViewInteraction.checkIsDisplayed(displayed: Boolean = true) {
    check(
        ViewAssertions.matches(
            if (displayed) ViewMatchers.isDisplayed() else Matchers.not(
                ViewMatchers.isDisplayed()
            )
        )
    )
}

fun ViewInteraction.checkWithText(text: String) {
    check(ViewAssertions.matches(ViewMatchers.withText(text)))
}

fun ViewInteraction.performScrollRecyclerTo(position: Int) {
    perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
}

fun <T : RecyclerView.ViewHolder> ViewInteraction.performActionOnRecyclerItemAtPosition(
    position: Int,
    action: ViewAction
) {
    perform(RecyclerViewActions.actionOnItemAtPosition<T>(position, action))
}

fun ViewInteraction.performClick() {
    perform(ViewActions.click())
}