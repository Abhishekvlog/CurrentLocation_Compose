package com.example.currentlocation.viewModel.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currentlocation.data.local.state.StateEntity
import com.example.currentlocation.data.local.state.StateDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StateViewModel(private val dao : StateDao) : ViewModel() {

    var data = listOf<StateEntity>()
    fun insertState(stateName : String){
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertState(StateEntity(stateName))
        }
    }
    suspend fun getState(): List<StateEntity> {
     return dao.getState()
    }
}
