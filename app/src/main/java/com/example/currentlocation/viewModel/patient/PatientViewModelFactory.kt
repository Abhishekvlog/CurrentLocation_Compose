package com.example.currentlocation.viewModel.patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currentlocation.data.local.patient.PatientDao

class PatientViewModelFactory (val dao : PatientDao) : ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PatientViewModel(dao) as T
    }
}