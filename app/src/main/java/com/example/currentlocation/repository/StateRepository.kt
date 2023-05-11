package com.example.currentlocation.repository

import com.example.currentlocation.model.CitiesModel
import com.example.currentlocation.model.RetrofitBuilder
import com.example.currentlocation.model.StateModel
import retrofit2.Response

class StateRepository() {

    suspend fun getStates():Response<StateModel>{
       return RetrofitBuilder.apiInterface.getStates("India")
    }

    suspend fun getCities() : Response<CitiesModel>{
        return RetrofitBuilder.apiInterface.getCity("India")
    }


}