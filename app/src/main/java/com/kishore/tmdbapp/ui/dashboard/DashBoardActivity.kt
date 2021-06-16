package com.kishore.tmdbapp.ui.dashboard

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.kishore.tmdbapp.R
import com.kishore.tmdbapp.databinding.ActivityDashboardBinding
import com.kishore.tmdbapp.databinding.ToolbarMainBinding
import com.kishore.tmdbapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class DashBoardActivity : BaseActivity() {
    val mTAG: String = DashBoardActivity::class.java.simpleName
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var toolBarBinding: ToolbarMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        toolBarBinding = binding.toolbar
        setContentView(binding.root)
        setSupportActionBar(toolBarBinding.toolbarMain)

    }

    override fun onResume() {
        super.onResume()
        binding.toolbar.toolbarMain.title = getString(R.string.app_name)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }
}