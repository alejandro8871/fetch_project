package com.fetch.fetch.data.repository

import com.fetch.fetch.data.local.HiringDAO
import com.fetch.fetch.data.model.Hiring
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomRepository @Inject constructor(
    var hiringDAO: HiringDAO
) {

    suspend fun insertAll(data: List<Hiring>) {
        hiringDAO.clearFetchItems()
        hiringDAO.insertFetchItems(data)
    }

    suspend fun cleanDatabase() {
        hiringDAO.clearFetchItems()
    }


    suspend fun getFetchItems(): List<Hiring> {
        return hiringDAO.getFetchItems()
    }
}