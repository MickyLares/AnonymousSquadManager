package com.miguellares.anonymoussquadmanager.service

import com.miguellares.anonymoussquadmanager.service.modelResponse.RouteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/directions/driving-car")
    suspend fun getRoute(
        @Query("api_key") apiKey: String,
        @Query("start", encoded = true) start: String,
        @Query("end", encoded = true) end: String
    ): Response<RouteResponse>
}



