package com.example.currentlocation.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    val BaseUrl = "https://countriesnow.space/api/v0.1/countries/"

    val retrofit = Retrofit.Builder().baseUrl(BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val apiInterface = retrofit.create(ApiInterface::class.java)

}