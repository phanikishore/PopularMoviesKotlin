package com.kishore.tmdbapp.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

abstract class BaseActivity :AppCompatActivity(){
    private val mTAG = BaseActivity::class.java.simpleName

    fun setToolbarTitle(title:String){
        supportActionBar?.title = title
    }

    fun setNavControllerAppBarConfig(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(
            navController,
            appBarConfig
        )
    }
}