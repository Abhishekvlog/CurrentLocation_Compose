package com.example.currentlocation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currentlocation.data.local.StateEntity
import com.example.currentlocation.data.local.StateDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StateViewModel(private val dao : StateDao) : ViewModel() {

    fun insertState(stateName : String){
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertState(StateEntity(stateName))
        }
    }
    fun getState(): List<StateEntity> {
        var data = listOf<StateEntity>()
        viewModelScope.launch(Dispatchers.IO) {
            data = dao.getState()
        }
        return data
    }
}
