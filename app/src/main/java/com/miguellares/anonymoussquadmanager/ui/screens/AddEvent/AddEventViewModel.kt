package com.miguellares.anonymoussquadmanager.ui.screens.AddEvent

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.miguellares.anonymoussquadmanager.models.Partida
import com.miguellares.anonymoussquadmanager.models.Usuario
import java.util.*

class AddEventViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _selectedDateText = MutableLiveData<String>()
    private val _selectField = MutableLiveData<String>()
    val selectField: LiveData<String> = _selectField
    private val _openDialog = MutableLiveData<Boolean>()
    val openDialog: LiveData<Boolean> = _openDialog
    val selectedDateText: LiveData<String> = _selectedDateText
    private val _typeEvent = MutableLiveData<String>()
    val typeEvent: LiveData<String> = _typeEvent
    private val _listField = MutableLiveData<List<String>>()
    val listField: LiveData<List<String>> = _listField
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val _isDateInPartida = MutableLiveData<Boolean>()
    val isDateInPartida: LiveData<Boolean> = _isDateInPartida
    private val _isDateInEntrenamiento = MutableLiveData<Boolean>()
    val isDateInEntrenamiento: LiveData<Boolean> = _isDateInEntrenamiento
    private val _isDateInPartidaE = MutableLiveData<Boolean>()
    val isDateInPartidaE: LiveData<Boolean> = _isDateInPartidaE


    fun getListOfField() {
        db.collection("Campo").get().addOnCompleteListener { task ->
            val lista = mutableListOf<String>()
            if (task.isSuccessful) {
                task.result.documents.forEach { documentSnapshot ->
                    lista.add(documentSnapshot.get("id").toString())
                }
            }
            _listField.value = lista
        }
    }

    fun addNewEvent(usuario: Usuario, typeEvent: String, field: String, date: String) {
            if (typeEvent.isNotEmpty() && field.isNotEmpty() && date.isNotEmpty()) {
                val idPartida = UUID.randomUUID().toString()
                var listaGamer = mutableListOf<String>()
                var partidas: MutableList<String> = mutableListOf()
                usuario.partidas.let {
                    it?.let { lPartidas -> partidas.addAll(lPartidas) }
                }
                partidas.add(idPartida)
                usuario.nickName?.let { listaGamer.add(it) }
                _loading.value = true
                db.collection(typeEvent).document(idPartida).set(
                    hashMapOf(
                        "id" to idPartida,
                        "campo" to field,
                        "jugadores" to listaGamer,
                        "fecha" to date,
                        "isOficial" to false
                    )
                )
                val usuarioDb = Firebase.firestore
                usuario.email?.let {
                    usuarioDb.collection("Usuario").document(it).set(
                        hashMapOf(
                            "nickName" to usuario.nickName,
                            "isAdmin" to usuario.isAdmin,
                            "email" to usuario.email,
                            "password" to usuario.password,
                            "partidas" to partidas
                        )
                    )
                }
                _loading.value = false
            }


    }

    fun selectField(value: String) {
        _selectField.value = value
    }

    fun onDissmisDialog(value: Boolean) {
        _openDialog.value = value
    }

    fun isDateInDB(date: String) {

        db.collection("Partida").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val list = mutableListOf<Partida>()
                task.result.documents.forEach {
                    val partida = Partida(
                        it.get("id").toString(),
                        it.get("campo").toString(),
                        it.get("jugadores") as List<String>,
                        it.get("fecha").toString(),
                        it.get("isOficial") as Boolean
                    )
                    if (partida.fecha == date) {
                        _isDateInPartida.value = true
                    }
                }

            }
        }
        db.collection("Partida Especial").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val list = mutableListOf<Partida>()
                task.result.documents.forEach {
                    val partida = Partida(
                        it.get("id").toString(),
                        it.get("campo").toString(),
                        it.get("jugadores") as List<String>,
                        it.get("fecha").toString(),
                        it.get("isOficial") as Boolean
                    )
                    if (partida.fecha == date) {
                        _isDateInPartidaE.value = true
                    }
                }

            }
        }
        db.collection("Entrenamiento").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val list = mutableListOf<Partida>()
                task.result.documents.forEach {
                    val partida = Partida(
                        it.get("id").toString(),
                        it.get("campo").toString(),
                        it.get("jugadores") as List<String>,
                        it.get("fecha").toString(),
                        it.get("isOficial") as Boolean
                    )
                    if (partida.fecha == date) {
                        _isDateInEntrenamiento.value = true
                    }
                }

            }
        }

    }

    fun showDatePickerDialog(context: Context, calendar: Calendar) {
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]


        val datePicker = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                _selectedDateText.value = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            }, year, month, dayOfMonth
        )
        datePicker.datePicker.minDate = calendar.timeInMillis
        datePicker.show()

    }

    fun onClickRadioButton(option: String) {
        _typeEvent.value = option
    }
}