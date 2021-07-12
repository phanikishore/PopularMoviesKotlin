package com.kishore.tmdbapp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.kishore.tmdbapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashScreenActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(SplashScreenActivity::class.java)

    @Test
    fun test_isActivity_Visible() {

        /*onView(withId(R.id.splashActivity))
            .check(matches(isDisplayed()))*/

        onView(withId(R.id.splashActivity))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        Thread.sleep(5000)
    }

    @Test
    fun test_NavToDashBoardActivity() {
        onView(withId(R.id.splashActivity)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(R.id.dashboardActivity)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(R.id.popularMovies)).check(matches(isDisplayed()))
        onView(withId(R.id.topMovies)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}