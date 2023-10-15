package com.miguellares.anonymoussquadmanager.ui.screens.MapScreen

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.compose.*
import com.miguellares.anonymoussquadmanager.MainActivity
import com.miguellares.anonymoussquadmanager.R


@SuppressLint("MissingPermission")
@Composable
fun MyGoogleMaps(
    name: String,
    viewModel: MapViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
   viewModel.getFieldByName(name)
  //  var end = "${viewModel.field.value?.longitude},${viewModel.field.value?.latitude}"
    val context = LocalContext.current
    val fusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var devicetLatLng by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }
    var startLatLng by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    var lastKnowLocation by remember {
        mutableStateOf<Location?>(null)
    }
    val route by viewModel.poliLyne.observeAsState(initial = PolylineOptions())
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(devicetLatLng, 19f)
    }
    val result = fusedLocationProviderClient.lastLocation
    result.addOnCompleteListener(context as MainActivity) { task ->
        if (task.isSuccessful) {
            lastKnowLocation = task.result
            devicetLatLng = LatLng(lastKnowLocation!!.latitude, lastKnowLocation!!.longitude)
            cameraPosition.position = CameraPosition.fromLatLngZoom(devicetLatLng, 19f)
            var start = "${devicetLatLng.longitude},${devicetLatLng.latitude}"
        }

    }

    Box(contentAlignment = Alignment.BottomCenter) {
        GoogleMap(
            cameraPositionState = cameraPosition,
            properties = MapProperties(mapType = MapType.TERRAIN, isMyLocationEnabled = true)
        ) {
            viewModel.field.value?.latitude?.toDouble()?.let { latitude ->
                viewModel.field.value?.longitude?.toDouble()?.let { longitude ->
                    LatLng(
                        latitude,
                        longitude
                    )
                }
            }?.let {
                Marker(
                    position = it
                )
            }
            Marker(position = devicetLatLng)
            Polyline(
                points = route.points,
                color = colorResource(id = R.color.YellowAnonymous)
            )

        }

      //  renderButton(viewModel = viewModel, start = start, end = end)
    }


}

@Composable
fun renderButton(viewModel: MapViewModel, start: String, end: String) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
        Button(onClick = {
            viewModel.beginCirculate(start, end)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Ir al campo")
        }
    }

}
