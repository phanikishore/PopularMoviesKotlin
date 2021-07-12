package com.kishore.tmdbapp.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kishore.tmdbapp.R
import com.kishore.tmdbapp.data.model.Movie
import com.kishore.tmdbapp.data.utils.Constants.THUMBNAIL_URL
import com.kishore.tmdbapp.databinding.ItemMovieInfoBinding
import com.kishore.tmdbapp.utils.*

class MovieListAdapter(val listener: IMovieItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mTAG = MovieListAdapter::class.java.simpleName
    private var mMovieList = mutableListOf<Movie?>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_info, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mMovieList[position]?.let {
            (holder as ItemViewHolder).bindItemInfo(it)
        }
    }

    override fun getItemCount() = mMovieList.size

    fun setAdapterData(movies: List<Movie?>) {
        mMovieList.clear()
        mMovieList.addAll(movies)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemMovieInfoBinding = ItemMovieInfoBinding.bind(view)

        fun bindItemInfo(movie: Movie) {
            binding.tvMovieTitle.text = movie.title ?: "No Title"
            binding.tvMovieReleaseDate.text = movie.releaseDate?.let {
                it.toStringFormatted(
                    STANDARD_DATE_FORMAT,
                    DATE_FORMAT_MONTH_DAY_YEAR
                )
            } ?: kotlin.run { "NA" }
            movie.posterPath?.run {
                binding.ivMoviePoster.loadUrl(this.prefixUrl(THUMBNAIL_URL))
            }
            binding.userScoreView.setPercentage(movie.voteAverage ?: 0.0)
            binding.root.setOnClickListener {
                listener.onMovieItemClicked(movie)
            }
        }
    }


}

interface IMovieItemClickListener {
    fun onMovieItemClicked(movie: Movie)
}
