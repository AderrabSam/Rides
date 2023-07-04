package com.assignment.rides.model


import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCall {

    @GET("/api/vehicle/random_vehicle")
    suspend fun getVehicles(@Query("size") size: Int): Response<List<VehiclesResponse>>

    companion object {

        var apiCallService: ApiCall? = null

        fun getInstance(): ApiCall {
            if (apiCallService == null) {
                val api = Retrofit.Builder()
                    .baseUrl("https://random-data-api.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                apiCallService = api.create(ApiCall::class.java)
            }
            return apiCallService!!
        }

    }
}