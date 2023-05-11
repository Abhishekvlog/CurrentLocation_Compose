package com.example.currentlocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.currentlocation.data.local.StateDatabase
import com.example.currentlocation.ui.theme.CurrentLocationTheme
import com.example.currentlocation.viewModel.CountryViewModel
import com.example.currentlocation.viewModel.StateViewModel
import com.example.currentlocation.viewModel.StateViewModelFactory
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : ComponentActivity() {
    private var locationCallback: LocationCallback? = null
    var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequired = false
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            StateDatabase::class.java,
            "state.db"
        ).build()
    }
    val mainViewModel: MainViewModel by viewModels<MainViewModel>()
    val countryViewModel by viewModels<CountryViewModel>()
    lateinit var stateViewModel : StateViewModel
//    val stateViewModel by viewModels<StateViewModel>(factoryProducer = {StateViewModelFactory(db.dao)})

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateViewModel = ViewModelProvider(this,StateViewModelFactory(db.dao)).get(StateViewModel::class.java)
        setContent {
            var latitude by remember {
                mutableStateOf(0.0)
            }
            var longitude by remember {
                mutableStateOf(0.0)
            }




//            countryViewModel.getCities()
//            countryViewModel.citiesLiveData.observe(this){cities ->
//                cities.forEach {
//                    Log.d("cities",it )
//                }
//            }

            CurrentLocationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val context = LocalContext.current
                    if (!mainViewModel.show.value) {
                        CircularProgressBar(isDisplay = true)
                    }
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    locationCallback = object : LocationCallback() {
                        override fun onLocationResult(p0: LocationResult) {
                            for (lo in p0.locations) {
                                // Update UI with location data
                                latitude = lo.latitude
                                longitude = lo.longitude
                            }
                        }
                    }

                    val launcherMultiplePermissions = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestMultiplePermissions()
                    ) { permissionsMap ->
                        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
                        if (areGranted) {
                            locationRequired = true
                            startLocationUpdates()
                            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        val permissions = arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        if (permissions.all {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    it
                                ) == PackageManager.PERMISSION_GRANTED
                            }) {
                            // Get the location
                            startLocationUpdates()
                            LaunchedEffect(true) {
                                delay(1000)
                                Log.d("abhishek", "onCreate: location fatching start")
                                mainViewModel.getLocation(longitude, latitude, this@MainActivity)
                            }

                        } else {
                            LaunchedEffect(true) {
                                delay(2000)
                                launcherMultiplePermissions.launch(permissions)
                            }
                        }
                        Log.d("sizeCheck", "onCreate ${stateViewModel.getState().size}")
                        if (stateViewModel.getState().isEmpty()){
                            countryViewModel.getStates()
                            countryViewModel.stateLiveData.observe(this@MainActivity) { data ->
                                lifecycleScope.launch(Dispatchers.IO) {
                                    data.forEach {
                                        stateViewModel.insertState(it.name)
                                        Log.d("abhishek: ", it.name.lowercase())
                                    }
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = colorResource(id = R.color.bar_bg))
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Text(
                                text = "Patient Registration",
                                color = Color.Black,
                                fontSize = 22.sp
                            )
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 14.dp, end = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Addresses",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                            Text(text = "page 3/3", color = Color.Gray, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Home address",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            EditField(
                                name = mainViewModel.pinCode.value,
                                hint = "Postal code*",
                                0.45f
                            ) {
                                mainViewModel.pinCode.value = it
                            }
                            EditField(name = mainViewModel.state.value, hint = "State", 0.9f) {
                                mainViewModel.state.value = it
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            EditField(
                                name = mainViewModel.fullAddress.value,
                                hint = "Address Line 1*",
                                size = 1f
                            ) {
                                mainViewModel.fullAddress.value = it
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            EditField(
                                name = mainViewModel.address_2.value,
                                hint = "Address Line 2r",
                                size = 1f
                            ) {
                                mainViewModel.address_2.value = it
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            EditField(
                                name = mainViewModel.city.value,
                                hint = "Town/City*",
                                size = 1f
                            ) {
                                mainViewModel.city.value = it
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            EditField(
                                name = mainViewModel.district.value,
                                hint = "District",
                                size = 1f
                            ) {
                                mainViewModel.district.value = it
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CardButton(name = "Preview") {
                            }
                        }

                    }

                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationCallback?.let {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        if (locationRequired) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        locationCallback?.let { fusedLocationClient?.removeLocationUpdates(it) }
    }


}


@Composable
fun EditField(name: String, hint: String, size: Float, update: (String) -> Unit) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(size)
            .padding(end = 16.dp),
        value = name,
        onValueChange = { update(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            focusedBorderColor = Color.Gray,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
        ),
        label = { Text(text = hint) },
        placeholder = { Text(text = hint) }
    )

}


@Composable
fun CardButton(name: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colorResource(id = R.color.purple),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(text = name, fontSize = 16.sp, color = Color.White)
        }
    }
}


