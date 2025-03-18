package com.fetch.fetch.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fetch.fetch.data.remote.HiringUiState
import com.fetch.fetch.data.repository.FetchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FetchRepository
) : ViewModel() {

    private val _fetchHiring = MutableStateFlow<HiringUiState>(HiringUiState.Loading)
    val fetchHiring: StateFlow<HiringUiState> = _fetchHiring

    init {
        fetchNewHiring()
    }

    private fun fetchNewHiring() {
        viewModelScope.launch {
            try {
                val items = repository.fetchHiring()
                _fetchHiring.value = HiringUiState.Success(items)
            } catch (e: Exception) {
                _fetchHiring.value = HiringUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}