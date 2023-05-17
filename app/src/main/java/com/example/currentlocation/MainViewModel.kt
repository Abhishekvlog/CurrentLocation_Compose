package com.example.currentlocation

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {
    var city = mutableStateOf("")
    var state = mutableStateOf("")
    var district = mutableStateOf("")
    var fullAddress = mutableStateOf("")
    var pinCode = mutableStateOf("")
    var address_2 = mutableStateOf("")
    var show = mutableStateOf(false)
    var isStateValid by mutableStateOf(false)

    fun getLocation(longitude: Double, latitude: Double, context : Context) {
        val geocoder: Geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1) as List<Address>
        fullAddress.value = addresses[0].getAddressLine(0)
        address_2.value = addresses[0].subLocality
        city.value = addresses[0].locality
        state.value = addresses[0].adminArea
        pinCode.value = addresses[0].postalCode
        district.value = addresses[0].adminArea
        show.value = true
    }
}