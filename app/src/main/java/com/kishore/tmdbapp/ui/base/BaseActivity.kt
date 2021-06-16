package com.kishore.tmdbapp.ui.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity :AppCompatActivity(){
    private val mTAG = BaseActivity::class.java.simpleName

    fun setToolbarTitle(title:String){
        supportActionBar?.title = title
    }

}