package com.barun.bankopen.ui.dashboard.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.barun.bankopen.databinding.FragmentDashBoardBinding
import com.barun.bankopen.ui.base.BaseActivity
import com.barun.bankopen.ui.dashboard.DashBoardViewModel
import com.barun.bankopen.ui.dashboard.adapter.RouteListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class DashBoardFragment : Fragment() {
    private val TAG = DashBoardFragment::class.java.simpleName
    private var binding: FragmentDashBoardBinding? = null
    private val viewModel: DashBoardViewModel by activityViewModels()
    private var adapter: RouteListAdapter = RouteListAdapter()
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
        viewModel.fetchRouteInfo((System.currentTimeMillis() / 60000).toInt())
        binding?.routeList?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.routeList?.adapter = adapter
        viewModel.routeInfoLiveData.observe(viewLifecycleOwner, { routeResponse ->
            Log.d(TAG, "Resposnse in DashBoardFragment::$routeResponse")
            adapter.addItems(routeResponse)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}