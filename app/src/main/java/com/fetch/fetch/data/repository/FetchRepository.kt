package com.fetch.fetch.data.repository

import android.util.Log
import com.fetch.fetch.data.local.HiringDAO
import com.fetch.fetch.data.model.Hiring
import com.fetch.fetch.data.remote.FetchService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchRepository @Inject constructor(
    var fetchService: FetchService,
    var hiringDAO: HiringDAO
) {

    suspend fun fetchHiring(): List<Hiring> {
        try {
            var response = fetchService.fetchHiring()
                .filter { !it.name.isNullOrBlank() }
                .sortedWith(compareBy({ it.listId }, { it.name }))

            hiringDAO.clearFetchItems()
            hiringDAO.insertFetchItems(response)
            Log.e("FetchRepository", "response =$response")
            return response
        } catch (e: Exception) {
            Log.e("FetchRepository", "Network Error: ${e.message}")

            return hiringDAO.getFetchItems()
        }
    }
}