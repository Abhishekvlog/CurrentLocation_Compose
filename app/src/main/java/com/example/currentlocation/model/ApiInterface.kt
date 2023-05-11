package com.example.currentlocation.model

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("states")
    suspend fun getStates(@Field("country") country: String): Response<StateModel>

    @FormUrlEncoded
    @POST("cities")
    suspend fun getCity(@Field("country") country: String): Response<CitiesModel>

}