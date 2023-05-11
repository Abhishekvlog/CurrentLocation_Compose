package com.example.currentlocation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currentlocation.model.CitiesModel
import com.example.currentlocation.model.State
import com.example.currentlocation.repository.StateRepository
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {

    val repository = StateRepository()
    private val stateMutableLiveData = MutableLiveData<List<State>>()
    val stateLiveData: LiveData<List<State>> = stateMutableLiveData

    private val citiesMutableLiveData = MutableLiveData<List<String>>()
    val citiesLiveData: LiveData<List<String>> = citiesMutableLiveData

    fun getStates() {
        viewModelScope.launch {
            val response = repository.getStates()
            stateMutableLiveData.value = response.body()?.data?.states
        }
    }

    fun getCities() {
        viewModelScope.launch {
            val response = repository.getCities()
            citiesMutableLiveData.value = response.body()?.data
        }
    }
}