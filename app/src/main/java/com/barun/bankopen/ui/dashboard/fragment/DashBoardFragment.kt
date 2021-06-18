package com.barun.bankopen.ui.dashboard.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.barun.bankopen.databinding.FragmentDashBoardBinding
import com.barun.bankopen.ui.base.BaseActivity
import com.barun.bankopen.ui.dashboard.DashBoardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class DashBoardFragment : Fragment() {
    private val TAG = DashBoardFragment::class.java.simpleName
    private var binding: FragmentDashBoardBinding? = null
    private val viewModel: DashBoardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashBoardBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as BaseActivity).setToolbarTitle("OpenBank")
        viewModel.fetRouteTimings()
        viewModel.routeListLiveData.observe(viewLifecycleOwner, { routeResponse ->
            Log.d(TAG, "Resposnse in DashBoardFragment::$routeResponse")
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}