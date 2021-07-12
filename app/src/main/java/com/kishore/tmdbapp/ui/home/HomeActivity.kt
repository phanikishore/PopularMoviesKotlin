package com.kishore.tmdbapp.ui.home

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.kishore.tmdbapp.R
import com.kishore.tmdbapp.databinding.ActivityHomeBinding
import com.kishore.tmdbapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    val mTAG: String = HomeActivity::class.java.simpleName
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }
}