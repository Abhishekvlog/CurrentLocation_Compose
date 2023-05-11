package com.example.currentlocation.model

data class CitiesModel(
    val `data`: List<String>,
    val error: Boolean,
    val msg: String
)