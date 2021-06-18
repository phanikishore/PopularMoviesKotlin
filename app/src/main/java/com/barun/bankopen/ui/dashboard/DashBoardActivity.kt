package com.barun.bankopen.ui.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.barun.bankopen.R
import com.barun.bankopen.databinding.ActivityDashboardBinding
import com.barun.bankopen.databinding.ToolbarMainBinding
import com.barun.bankopen.sync.SyncViewModel
import com.barun.bankopen.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class DashBoardActivity : BaseActivity() {

    val mTAG: String = DashBoardActivity::class.java.simpleName
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var toolBarBinding: ToolbarMainBinding
    private val viewModel: SyncViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        toolBarBinding = binding.toolbar
        setContentView(binding.root)
        setSupportActionBar(toolBarBinding.toolbarMain)
        viewModel.startWork()
    }

    override fun onResume() {
        super.onResume()
        binding.toolbar.toolbarMain.title = getString(R.string.app_name)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }
}