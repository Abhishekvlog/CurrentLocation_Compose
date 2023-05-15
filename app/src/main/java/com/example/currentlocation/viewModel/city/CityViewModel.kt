package com.example.currentlocation.viewModel.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currentlocation.data.local.city.CityDao
import com.example.currentlocation.data.local.city.CityEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityViewModel(private val dao : CityDao) : ViewModel() {

    var data = listOf<CityEntity>()
    fun insertCity(stateName : String){
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertCity(CityEntity(stateName))
        }
    }
    suspend fun getCity(): List<CityEntity> {
        return dao.getCity()
    }
}