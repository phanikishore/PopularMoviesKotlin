package com.kishore.tmdbapp.ui.base

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

/**
 * class BaseFragment used for
 */
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    private val mTAG = BaseFragment::class.java.simpleName

    fun setNavControllerAppBarConfig(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        (requireActivity() as BaseActivity).setupActionBarWithNavController(
            navController,
            appBarConfig
        )
    }

}