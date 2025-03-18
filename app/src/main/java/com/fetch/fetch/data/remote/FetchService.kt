package com.fetch.fetch.data.remote

import com.fetch.fetch.data.model.Hiring
import retrofit2.http.GET

interface FetchService {

    @GET("hiring.json")
    suspend fun fetchHiring(): List<Hiring>
}