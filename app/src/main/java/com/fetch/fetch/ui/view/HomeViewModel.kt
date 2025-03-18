package com.fetch.fetch.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fetch.fetch.data.remote.HiringUiState
import com.fetch.fetch.data.repository.FetchRepository
import com.fetch.fetch.data.repository.RoomRepository
import com.fetch.fetch.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchRepository: FetchRepository,
    private var roomRepository: RoomRepository,
    private val logger: Logger
) : ViewModel() {

    private val _fetchHiring = MutableStateFlow<HiringUiState>(HiringUiState.Loading)
    val fetchHiring: StateFlow<HiringUiState> = _fetchHiring

    init {
        fetchNewHiring()
    }

    private fun fetchNewHiring() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val cachedItems = roomRepository.getFetchItems()

                    // If cache is not empty, use the cached data
                    if (cachedItems.isNotEmpty()) {
                        _fetchHiring.value = HiringUiState.Success(cachedItems)
                    }
                }
                // First, check if we have data in the local cache
                when (val result = fetchRepository.fetchHiring()) {
                    is FetchRepository.Result.Success -> {
                        // If the response is successful, update UI state and save data to the room database
                        if (result.data.isNotEmpty()) {
                            withContext(Dispatchers.IO) {
                                roomRepository.cleanDatabase()  // Clean Room database
                                roomRepository.insertAll(result.data)  // Insert data into Room
                            }
                        }
                        _fetchHiring.value = HiringUiState.Success(result.data)
                    }

                    is FetchRepository.Result.Error -> {
                        // If there is an error, propagate the error message to the UI
                        logger.e(
                            "HomeViewModel",
                            "Error fetching hiring: ${result.exception.message}",
                            result.exception
                        )
                        val cachedItems = roomRepository.getFetchItems()
                        if (cachedItems.isNotEmpty()) {
                            _fetchHiring.value = HiringUiState.Success(cachedItems)
                        } else {
                            _fetchHiring.value =
                                HiringUiState.Error("Error fetching hiring: ${result.exception.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                // In case of an exception, show cached data if available
                _fetchHiring.value = HiringUiState.Error(e.localizedMessage ?: "Unknown error")
                val cachedItems = roomRepository.getFetchItems()
                if (cachedItems.isNotEmpty()) {
                    _fetchHiring.value = HiringUiState.Success(cachedItems)
                }
            }


        }
    }
}