package com.fetch.fetch.data.repository

import com.fetch.fetch.data.model.Hiring
import com.fetch.fetch.data.remote.FetchService
import com.fetch.fetch.utils.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchRepository @Inject constructor(
    var fetchService: FetchService,
    private val logger: Logger // Inject the logger
) {


    sealed class Result {
        data class Success(val data: List<Hiring>) : Result()
        data class Error(val exception: Exception) : Result()
    }


    suspend fun fetchHiring(): Result {
        return try {
            val response = fetchService.fetchHiring()
                .filterNot { it.name.isNullOrBlank() }
                .sortedWith(
                    compareBy<Hiring> { it.listId }
                        .thenBy { it.name?.extractNumber() }
                )

            Result.Success(response)
        } catch (e: Exception) {
            // Log the error properly
            logger.e("FetchRepository", "Network Error: ${e.message}", e)
            Result.Error(e)
        }
    }
}

fun String.extractNumber(): Int {
    return this.filter { it.isDigit() }.toIntOrNull() ?: Int.MAX_VALUE
}