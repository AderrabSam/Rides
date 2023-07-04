package com.assignment.rides.model

class Repository(private val apiCall: ApiCall) {

    suspend fun getVehicles(size: Int) = apiCall.getVehicles(size)
}