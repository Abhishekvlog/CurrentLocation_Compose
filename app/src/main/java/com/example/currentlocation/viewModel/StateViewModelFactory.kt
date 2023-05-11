package com.example.currentlocation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currentlocation.data.local.StateDao

class StateViewModelFactory(val dao : StateDao) : ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StateViewModel(dao) as T
    }
}