package com.barun.bankopen.ui.dashboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.barun.bankopen.databinding.FragmentDashBoardBinding
import com.barun.bankopen.ui.dashboard.DashBoardViewModel
import com.barun.bankopen.ui.dashboard.adapter.RouteListAdapter
import com.barun.bankopen.utils.convertToMinutes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi
import java.text.SimpleDateFormat
import java.util.*

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
        val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        viewModel.fetchRouteInfo((sdf.format(Date()).convertToMinutes()))
        binding?.routeList?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.routeList?.adapter = adapter
        viewModel.routeInfoLiveData.observe(viewLifecycleOwner, { routeResponse ->
            binding?.progressBarDashBoard?.isVisible = false
            adapter.addItems(routeResponse, sdf.format(Date()).convertToMinutes())
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}