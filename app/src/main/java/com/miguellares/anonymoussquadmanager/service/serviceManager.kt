package com.miguellares.anonymoussquadmanager.service

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.miguellares.anonymoussquadmanager.service.modelResponse.RouteResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceManager {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openrouteservice.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createRoute(start: String, end: String, home: (RouteResponse?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java)
                .getRoute("5b3ce3597851110001cf6248537246db120141dd8074d051e718b750", start, end)
            if (call.isSuccessful) {
                home(call.body())
            } else {

            }
        }
    }
}