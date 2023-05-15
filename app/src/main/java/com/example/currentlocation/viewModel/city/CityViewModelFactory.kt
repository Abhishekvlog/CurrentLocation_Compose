package com.example.currentlocation.viewModel.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currentlocation.data.local.city.CityDao

class CityViewModelFactory(val dao : CityDao) : ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CityViewModel(dao) as T
    }
}