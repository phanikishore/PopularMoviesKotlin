package com.kishore.tmdbapp.ui.dashboard.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.kishore.tmdbapp.R
import com.kishore.tmdbapp.databinding.FragmentViewMoreBinding
import com.kishore.tmdbapp.ui.base.BaseActivity
import com.kishore.tmdbapp.ui.base.BaseFragment
import com.kishore.tmdbapp.ui.dashboard.adapter.IMovieItemClickListener
import com.kishore.tmdbapp.ui.dashboard.adapter.MovieListAdapter
import com.kishore.tmdbapp.ui.dashboard.viewmodel.DashBoardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class ViewMoreFragment : BaseFragment(R.layout.fragment_view_more) {
    val mTAG = ViewMoreFragment::class.java.simpleName
    var binding: FragmentViewMoreBinding? = null
    private var onMovieItemClickListener = object : IMovieItemClickListener {
        override fun onMovieItemClicked(movie: com.kishore.tmdbapp.data.model.Movie) {
            //findNavController().navigate(ViewMoreFragmentDirections.actionViewMoreFragmentToViewDetailsFragment(movie))
        }
    }
    val viewModeAdapter by lazy { MovieListAdapter(onMovieItemClickListener) }
    val viewModel: DashBoardViewModel by activityViewModels()
   // val args: ViewMoreFragmentArgs by navArgs()
    val configuration by lazy {
        viewModel.configuration
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewMoreBinding.bind(view)
        binding?.let {
            it.rvViewMore.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = viewModeAdapter
            }
        }
        /*(requireActivity() as BaseActivity).apply {
            setSupportActionBar(binding?.toolbar?.toolbarMain)
            setToolbarTitle(args.categoryType)
        }
        Log.d(mTAG, args.categoryType)
        (requireActivity() as BaseActivity).setToolbarTitle(args.categoryType)*/
        viewModel.popularMovies.observe(requireActivity(), { it ->
            it?.let { response ->
                response.movies?.let { it1 ->
                    it1.let {
                        viewModeAdapter.setAdapterData(it)
                    }
                }
            }
        })
        /*when (args.categoryType) {
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
        }*/
    }

}