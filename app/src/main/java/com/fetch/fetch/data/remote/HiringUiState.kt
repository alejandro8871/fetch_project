package com.fetch.fetch.data.remote

import com.fetch.fetch.data.model.Hiring

sealed class HiringUiState {
    object Loading : HiringUiState()
    data class Success(val items: List<Hiring>) : HiringUiState()
    data class Error(val message: String) : HiringUiState()
}