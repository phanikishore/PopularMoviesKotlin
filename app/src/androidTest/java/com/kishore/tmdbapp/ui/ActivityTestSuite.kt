package com.kishore.tmdbapp.ui

import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * class ActivityTestSuite used for
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(SplashScreenActivityTest::class)
class ActivityTestSuite {
    private val mTAG = ActivityTestSuite::class.java.simpleName
}