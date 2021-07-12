package com.kishore.tmdbapp.ui.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kishore.tmdbapp.data.model.Configuration
import com.kishore.tmdbapp.data.model.DashBoardResponse
import com.kishore.tmdbapp.data.model.MoviesResponse
import com.kishore.tmdbapp.data.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class DashBoardViewModel @Inject constructor(val repository: TmdbRepository) : ViewModel() {
    private val mTAG = DashBoardViewModel::class.java.simpleName
    val popularMovies = MutableLiveData<MoviesResponse>()
    val dashBoardData = MutableLiveData<DashBoardResponse>()
    var configuration: Configuration? = null
    var pageNo: Int = 1

    init {
        viewModelScope.launch {
            configuration = repository.fetchConfiguration()
        }
    }

    fun fetchPopularMovies() {

        viewModelScope.launch {
            repository.fetchPopularMovies(pageNo).let {
                popularMovies.value = it
            }
        }
    }

    fun fetchTopMovies() {
        viewModelScope.launch {
            repository.fetchTopMovies(pageNo).let {
                popularMovies.value = it
            }
        }
    }

    fun fetchDashBoardData() {
        viewModelScope.launch {
            repository.fetchDashBoardResponse().let {
                dashBoardData.value = it
            }
        }
    }

    fun latestMovies() {
        viewModelScope.launch {
            repository.fetchLatestMovies().let {
                popularMovies.value = it
            }
        }
    }

    fun fetchUpComingMovies() {
        viewModelScope.launch {
            repository.fetchUpComingMovies(pageNo).let {
                popularMovies.value = it
            }
        }
    }
}
