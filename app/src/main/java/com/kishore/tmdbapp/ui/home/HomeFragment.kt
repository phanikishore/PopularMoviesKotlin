package com.kishore.tmdbapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.kishore.tmdbapp.R
import com.kishore.tmdbapp.databinding.FragmentHomeBinding
import com.kishore.tmdbapp.ui.base.BaseActivity
import com.kishore.tmdbapp.ui.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    lateinit var viewBinding: FragmentHomeBinding
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentHomeBinding.bind(view)
        (requireActivity() as BaseActivity).apply {
            setSupportActionBar(viewBinding.toolbarMainInclude.toolbarMain)
            setToolbarTitle("TMDB App")
        }
        val navHomeFragment =
            childFragmentManager.findFragmentById(R.id.nav_home_fragment) as NavHostFragment
        navController = navHomeFragment.navController

        viewBinding.bnvHome.bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashboardMenu,
                R.id.discoverMenu,
                R.id.tvMenu,
                R.id.watchListMenu,
                R.id.profileMenu
            )
        )

        setNavControllerAppBarConfig(navController, appBarConfiguration)
    }
}