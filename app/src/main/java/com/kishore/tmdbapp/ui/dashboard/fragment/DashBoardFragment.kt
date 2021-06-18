package com.kishore.tmdbapp.ui.dashboard.fragment

import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kishore.tmdbapp.R
import com.kishore.tmdbapp.data.model.setPosterWith
import com.kishore.tmdbapp.data.repository.TmdbRepository
import com.kishore.tmdbapp.databinding.FragmentDashBoardBinding
import com.kishore.tmdbapp.ui.base.BaseActivity
import com.kishore.tmdbapp.ui.dashboard.adapter.IMovieItemClickListener
import com.kishore.tmdbapp.ui.dashboard.viewmodel.DashBoardViewModel
import com.kishore.tmdbapp.ui.dashboard.adapter.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@AndroidEntryPoint
class DashBoardFragment : Fragment() {
    private val mTAG = DashBoardFragment::class.java.simpleName
    private var binding: FragmentDashBoardBinding? = null
    private val viewModel: DashBoardViewModel by activityViewModels()
    private var onMovieItemClickListener = object : IMovieItemClickListener {
        override fun onMovieItemClicked(movie: com.kishore.tmdbapp.data.model.Movie) {
            val action =
                DashBoardFragmentDirections.actionDashBoardFragmentToViewDetailsFragment(movie)
            findNavController().navigate(action)
        }
    }
    var popularMoviesListAdapter: MovieListAdapter = MovieListAdapter(onMovieItemClickListener)
    var topMoviesListAdapter: MovieListAdapter = MovieListAdapter(onMovieItemClickListener)

    @Inject
    lateinit var repo: TmdbRepository
    val configuration by lazy {
        viewModel.configuration
    }

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
        (requireActivity() as BaseActivity).apply {
            setSupportActionBar(binding?.toolbarMainInclude?.toolbarMain)
            setToolbarTitle("TMDB App")
        }
        viewModel.dashBoardData.observe(requireActivity(), {
            it?.let {
                it.popularMovies?.movies?.map { movie ->
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
                        popularMoviesListAdapter.setAdapterData(it)
                        Log.d(mTAG, "Size: ${popularMoviesListAdapter.itemCount}")
                    }

                }
                it.topMovies?.movies?.map { movie ->
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
                        topMoviesListAdapter.setAdapterData(it)
                    }
                }
            }
        })
        binding?.let {
            it.popularMovies.setCategoryAdapter(
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                ), popularMoviesListAdapter
            )
            it.topMovies.setCategoryAdapter(
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                ), topMoviesListAdapter
            )
            it.popularMovies.setOnViewMoreClickListener(onClickListener)
            it.topMovies.setOnViewMoreClickListener(onClickListener)
        }

        viewModel.fetchDashBoardData()
        viewModel.fetchRouteResponse()
    }

    var onClickListener = View.OnClickListener { view ->
        view?.let {
            val tag = it.tag as String
            Log.d(mTAG, "OnClick Listener Called for $tag")
            val action = DashBoardFragmentDirections.actionDashBoardFragmentToViewMoreFragment(tag)
            it.findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}