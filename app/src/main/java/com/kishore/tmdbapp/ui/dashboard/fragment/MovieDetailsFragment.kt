package com.kishore.tmdbapp.ui.dashboard.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.kishore.tmdbapp.R
import com.kishore.tmdbapp.data.utils.Constants.BACKDROP_URL
import com.kishore.tmdbapp.databinding.FragmentViewDetailsBinding
import com.kishore.tmdbapp.ui.base.BaseActivity
import com.kishore.tmdbapp.ui.base.BaseFragment
import com.kishore.tmdbapp.ui.dashboard.viewmodel.DashBoardViewModel
import com.kishore.tmdbapp.utils.loadUrl
import com.kishore.tmdbapp.utils.prefixUrl
import dagger.hilt.android.AndroidEntryPoint

/**
 * View Binding example with a fragment that uses the alternate constructor for inflation and
 * [onViewCreated] for binding.
 * Source: https://github.com/android/architecture-components-samples/blob/main/ViewBindingSample/app/src/main/java/com/android/example/viewbindingsample/BindFragment.kt
 */
@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment(R.layout.fragment_view_details) {

    private var fragmentBinding: FragmentViewDetailsBinding? = null
//    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: DashBoardViewModel by activityViewModels()
    private val configuration by lazy {
        viewModel.configuration
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FragmentViewDetailsBinding.bind(view)
        //val toolbarBinding = fragmentBinding?.includeCollapsableToolBar
        (requireActivity() as BaseActivity).apply {
            //setSupportActionBar(toolbarBinding?.detailToolBar!!)
            //setToolbarTitle("New Tool Bar")
            //toolbarBinding.detailToolBar.title = "New ToolBar"
//            setToolbarTitle(args.movie.title!!)

        }
    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        fragmentBinding = null
        super.onDestroyView()
    }
}