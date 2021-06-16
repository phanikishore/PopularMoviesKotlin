package com.kishore.tmdbapp.ui.dashboard.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.kishore.tmdbapp.data.model.setPosterWith
import com.kishore.tmdbapp.data.network.ApiService
import com.kishore.tmdbapp.data.repository.TmdbRepository
import com.kishore.tmdbapp.databinding.FragmentViewMoreBinding
import com.kishore.tmdbapp.ui.base.BaseActivity
import com.kishore.tmdbapp.ui.dashboard.DashBoardViewModel
import com.kishore.tmdbapp.ui.dashboard.adapter.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@AndroidEntryPoint
class ViewMoreFragment : Fragment() {
    val mTAG = ViewMoreFragment::class.java.simpleName
    var binding: FragmentViewMoreBinding? = null
    val viewModeAdapter by lazy { MovieListAdapter() }
    val viewModel: DashBoardViewModel by activityViewModels()
    val args: ViewMoreFragmentArgs by navArgs()
    val configuration by lazy {
        viewModel.configuration
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewMoreBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.rvViewMore.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = viewModeAdapter
            }
        }
        Log.d(mTAG, args.categoryType)
        (requireActivity() as BaseActivity).setToolbarTitle(args.categoryType)
        viewModel.popularMovies.observe(requireActivity(), { it ->
            it?.let { response ->
                response.movies?.map { movie ->
                    configuration?.let { config ->
                        config.images?.posterSizes?.let { posterList ->
                            movie.setPosterWith(
                                configuration?.images?.secureBaseUrl!!,
                                posterList[4]!!
                            )
                        }
                    }
                }.also { it1 ->
                    it1?.let {
                        viewModeAdapter.setAdapterData(it)
                    }
                }
            }
        })
        when (args.categoryType) {
            "Popular Movies" -> {
                viewModel.fetchPopularMovies()
            }
            "Top Rated" -> {
                viewModel.fetchTopMovies()
            }
            "Latest Movies" -> {
                viewModel.latestMovies()
            }
            "UpComing Movies" -> {
                viewModel.fetchUpComingMovies()
            }
        }
    }

}