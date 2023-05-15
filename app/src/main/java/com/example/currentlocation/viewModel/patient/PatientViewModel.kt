package com.example.currentlocation.viewModel.patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currentlocation.data.local.patient.PatientDao
import com.example.currentlocation.data.local.patient.PatientEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PatientViewModel(private val dao: PatientDao) : ViewModel() {
    var data = listOf<PatientEntity>()
    fun insertPatientDetails(stateId: Int, cityId: Int, pinCodeId: Int, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertPatientDetail(
                patientEntity = PatientEntity(
                    address = address,
                    stateId = stateId,
                    cityId = cityId,
                    pinCodeId = pinCodeId
                )
            )
        }
    }

    suspend fun getState(): List<PatientEntity> {
        return dao.getAllPatientData()
    }

}