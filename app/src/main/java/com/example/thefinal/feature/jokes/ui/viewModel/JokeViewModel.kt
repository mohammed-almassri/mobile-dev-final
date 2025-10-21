package com.example.notthefinal.feature.posts.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.notthefinal.feature.posts.data.repository.JokeRepository
import com.example.notthefinal.feature.posts.ui.state.JokessUiState
import com.example.thefinal.feature.jokes.worker.JokeWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class JokeViewModel(val jokeRepository: JokeRepository): ViewModel() {
    private val _uiState = MutableStateFlow(JokessUiState.Empty)
    val uiState = _uiState.asStateFlow()

    companion object {
        private const val UNIQUE_WORK_NAME = "joke_work"
    }
//
    init {
        fetchJoke()
    startWork()
    }

    fun startWork() {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                fetchJoke()
                delay(30*60*1000)
            }
        }
    }


    fun fetchJoke(){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                jokeRepository.fetchJoke()
            }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    joke = res,
                    error = null
                )
        }
    }
}