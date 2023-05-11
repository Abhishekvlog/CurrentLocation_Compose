package com.example.currentlocation.model

data class Data(
    val iso2: String,
    val iso3: String,
    val name: String,
    val states: List<State>
)