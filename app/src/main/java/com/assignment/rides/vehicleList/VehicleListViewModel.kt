package com.assignment.rides.vehicleList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.assignment.rides.model.Repository
import com.assignment.rides.model.VehiclesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class VehicleListViewModel(private val repository: Repository) : ViewModel() {
    private val _listVehicles: MutableLiveData<List<VehiclesResponse>> = MutableLiveData()
    val listVehicles: LiveData<List<VehiclesResponse>> get() = _listVehicles
    fun getVehicle(size: Int) {
        viewModelScope.launch {
            val response = repository.getVehicles(size)
            withContext(Dispatchers.IO) {
                if (response.isSuccessful)
                    _listVehicles.postValue(response.body())
            }
        }
    }
}

class VehicleListViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VehicleListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}