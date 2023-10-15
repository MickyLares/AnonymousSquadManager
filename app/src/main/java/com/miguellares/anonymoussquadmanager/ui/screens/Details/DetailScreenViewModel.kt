package com.miguellares.anonymoussquadmanager.ui.screens.Details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.miguellares.anonymoussquadmanager.models.Partida
import com.miguellares.anonymoussquadmanager.models.Usuario

class DetailScreenViewModel : ViewModel() {
    private val _nameInvited = MutableLiveData<String>()
    private val _listaInvitadosRemoved = MutableLiveData<List<String>>()
    val listaInvitadosRemoved: LiveData<List<String>> = _listaInvitadosRemoved
    val nameInvited: LiveData<String> = _nameInvited
    val db = Firebase.firestore

    fun addInvited(name: String, partida: Partida, usuario: Usuario) {
        var mPartida = partida
        var lista = mutableListOf<String>()
        lista.addAll(mPartida.jugadores)
        lista.add(name)
        mPartida.jugadores = lista
        db.collection("Partida").document(partida.id).set(
            hashMapOf(
                "id" to mPartida.id,
                "campo" to mPartida.campo,
                "jugadores" to mPartida.jugadores,
                "fecha" to mPartida.fecha,
                "isOficial" to mPartida.isOficial
            )
        )
    }

    fun clickOnInvitedName(from: String, name: String, partida: Partida) {
        (partida.jugadores as MutableList<String>).remove(name)
        db.collection(from).document(partida.id).update("jugadores", partida.jugadores)
    }

    fun onNameInvitedChange(name: String) {
        _nameInvited.value = name
    }


}