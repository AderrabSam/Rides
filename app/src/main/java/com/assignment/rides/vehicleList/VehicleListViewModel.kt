package com.assignment.rides.vehicleList

import android.util.Log
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

    private val _newListVehicles: MutableLiveData<List<VehiclesResponse>> = MutableLiveData()
    val newListVehicles: LiveData<List<VehiclesResponse>> get() = _newListVehicles
    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading
    private val _hideLoading = MutableLiveData<Boolean>()
    val hideLoading: LiveData<Boolean> get() = _hideLoading
    fun getVehicle(size: Int) {
        _showLoading.postValue(true)
        viewModelScope.launch {
            val response = repository.getVehicles(size)
            withContext(Dispatchers.IO) {
                if (response.isSuccessful) {
                    _listVehicles.postValue(response.body())
                    _hideLoading.postValue(true)
                } else {
                    Log.d("Error", "API call failed: ${response.code()}+ ${response.message()}")
                    _hideLoading.postValue(true)
                }
            }
        }
    }

    fun getNewVehicle(size: Int) {
        viewModelScope.launch {
            val response = repository.getVehicles(size)
            withContext(Dispatchers.IO) {
                if (response.isSuccessful) {
                    _newListVehicles.postValue(response.body())
                } else {
                    Log.d("Error", "API call failed: ${response.code()}+ ${response.message()}")

                }
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