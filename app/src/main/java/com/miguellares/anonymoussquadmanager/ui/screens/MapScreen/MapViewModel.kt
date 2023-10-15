package com.miguellares.anonymoussquadmanager.ui.screens.MapScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.miguellares.anonymoussquadmanager.models.Campo
import com.miguellares.anonymoussquadmanager.service.ServiceManager
import com.miguellares.anonymoussquadmanager.service.modelResponse.RouteResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private var serviceManager = ServiceManager()
    private val _poliLyne = MutableLiveData(PolylineOptions())
    val poliLyne: LiveData<PolylineOptions> = _poliLyne
    val db = Firebase.firestore
    private val _field = MutableLiveData(Campo())
    val field: LiveData<Campo> = _field

    fun getFieldByName(name: String) {
        db.collection("Campo").document(name).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val campo = Campo(
                    task.result.get("id").toString(),
                    task.result.get("latitude").toString(),
                    task.result.get("longitude").toString(),
                    task.result.get("telefono").toString()
                )
                _field.value = campo
            }
        }
    }

    fun beginCirculate(start: String, end: String) {
        serviceManager.createRoute(start, end) {
            GlobalScope.launch {
                _poliLyne.postValue(drawRoute(it))
            }

        }
    }

    private fun drawRoute(response: RouteResponse?): PolylineOptions {
        val polyLineOption = PolylineOptions()
        response?.features?.first()?.geometry?.coordinates?.forEach {
            polyLineOption.add(LatLng(it[1], it[0]))
        }
        return polyLineOption

    }

}