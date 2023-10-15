package com.miguellares.anonymoussquadmanager.ui.screens.Main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.miguellares.anonymoussquadmanager.models.Notificaciones
import com.miguellares.anonymoussquadmanager.models.Partida
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.navigation.AppScreen
import com.miguellares.anonymoussquadmanager.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

@Suppress("UNCHECKED_CAST")
class MainViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    private val _onClickExit = MutableLiveData<Boolean>()
    val onClickExit: LiveData<Boolean> = _onClickExit
    private val _onClickBotonInvite = MutableLiveData<Boolean>()
    val onClickBotonInvite: LiveData<Boolean> = _onClickBotonInvite
    private val _partidas = MutableLiveData<List<Partida>>()
    val partidas: LiveData<List<Partida>> = _partidas
    private val _especial = MutableLiveData<List<Partida>>()
    val especial: LiveData<List<Partida>> = _especial
    private val _entrenamientos = MutableLiveData<List<Partida>>()
    val entrenamientos: LiveData<List<Partida>> = _entrenamientos
    private val _IsEvent = MutableLiveData(false)
    private val _isEntrenamiento = MutableLiveData<Boolean>(false)
    val isEntrenamiento: LiveData<Boolean> = _isEntrenamiento
    private val _isEspecialGame = MutableLiveData<Boolean>(false)
    val isEspecialGame: LiveData<Boolean> = _isEspecialGame
    val isEvent: LiveData<Boolean> = _IsEvent
    private val _nameInvited = MutableLiveData<String>()
    val nameInvited: LiveData<String> = _nameInvited
    private val _nameField = MutableLiveData<String>()
    val nameField: LiveData<String> = _nameField
    private val _inscribeMe = MutableLiveData<String>()
    val inscribeMe: LiveData<String> = _inscribeMe
    private val _openDialogPerfil = MutableLiveData<Boolean>()
    val openDialogPerfil: LiveData<Boolean> = _openDialogPerfil
    private val _ExistNotification = MutableLiveData<Boolean>()
    val ExistNotification: LiveData<Boolean> = _ExistNotification
    private val _notificacion = MutableLiveData<Notificaciones>()
    val notificaciones: LiveData<Notificaciones> = _notificacion
    private val _dateMovil = MutableLiveData<String>()
    val dateMovil: LiveData<String> = _dateMovil

    fun getDateMovil() {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDateAndTime = sdf.format(Date())
        var day = ""
        if (currentDateAndTime[0] == '0') {
            day = "${currentDateAndTime[1]}"
        } else {
            day = "${currentDateAndTime[0]}${currentDateAndTime[1]}"
        }
        var month = ""
        if (currentDateAndTime[3] == '0') {
            month = "${currentDateAndTime[4]}"
        } else {
            month = "${currentDateAndTime[3]}${currentDateAndTime[4]}"
        }
        var year =
            "${currentDateAndTime[6]}${currentDateAndTime[7]}${currentDateAndTime[8]}${currentDateAndTime[9]}"
        _dateMovil.value = "$day/$month/$year"
    }

    fun openDialogPerfil(value: Boolean) {
        _ExistNotification.value = value
    }


    fun onClickAddEvent(value: Boolean) {
        _openDialogPerfil.value = value
    }

    fun logoutUser(navController: NavController) {
        auth.signOut()
        navController.navigate(AppScreen.LoginScreen.route)
    }

    fun onClickExit(value: Boolean) {
        _onClickExit.value = value
    }

    fun onClickInscribeMe(partida: Partida, usuario: Usuario) {
        _inscribeMe.value = partida.id
        val usuarioDb = Firebase.firestore
        var lista = mutableListOf<String>()
        usuario.partidas?.let {
            lista = it as MutableList<String>
        }
        inscribeMe.value?.let { lista.add(it) }
        usuario.email?.let {
            usuario.partidas = lista
            usuarioDb.collection("Usuario").document(it).set(
                hashMapOf(
                    "email" to usuario.email,
                    "isAdmin" to usuario.isAdmin,
                    "nickName" to usuario.nickName,
                    "password" to usuario.password,
                    "partidas" to usuario.partidas
                )
            )
        }
    }

    fun checkNotifications(email: String) {
        db.collection(Utils.NOTIFICACIONES).document(Utils.AVISO).get()
            .addOnSuccessListener { task ->
                if (task.data != null) {
                    val notificaciones = Notificaciones(
                        task.get("id") as String,
                        task.get("message") as String,
                        task.get("vistoPor") as List<String>
                    )
                    _notificacion.value = notificaciones
                    if (!notificaciones.vistoPor?.contains(email)!!) {
                        _ExistNotification.value = true
                    }

                } else {
                    _notificacion.value = Notificaciones("", "", emptyList())
                    _ExistNotification.value = false
                }

            }
    }

    fun addVistoPor(usuario: Usuario, notificaciones: Notificaciones) {
        var lista = mutableListOf<String>()
        notificaciones.vistoPor?.let { lista.addAll(it) }
        usuario.email?.let { lista.add(it) }
        notificaciones.vistoPor = lista
        db.collection(Utils.NOTIFICACIONES).document(Utils.AVISO).set(
            hashMapOf(
                "id" to notificaciones.id,
                "message" to notificaciones.message,
                "vistoPor" to notificaciones.vistoPor
            )
        )
    }

    fun getAllGames(evento: String, showError: () -> Unit) {
        var _mPartidas: MutableList<Partida> = mutableListOf()
        var _mEntrenamientos: MutableList<Partida> = mutableListOf()
        var _mEspecial: MutableList<Partida> = mutableListOf()

        when (evento) {
            "Partida" -> {
                db.collection(evento).get().addOnSuccessListener { documents ->
                    documents?.forEach { document ->
                        var partida = Partida(
                            document.get("id") as String,
                            document.get("campo") as String,
                            document.get("jugadores") as List<String>,
                            document.get("fecha") as String,
                            document.get("isOficial") as Boolean
                        )
                        _mPartidas.add(partida)
                        _partidas.value = _mPartidas
                        if (_mPartidas.isNotEmpty()) {
                            _IsEvent.value = true
                        }
                    }

                }
            }
            "Entrenamiento" -> {
                db.collection(evento).get().addOnSuccessListener { documents ->
                    documents?.forEach { document ->
                        var partida = Partida(
                            document.get("id") as String,
                            document.get("campo") as String,
                            document.get("jugadores") as List<String>,
                            document.get("fecha") as String,
                            document.get("isOficial") as Boolean
                        )
                        _mEntrenamientos.add(partida)
                        _entrenamientos.value = _mEntrenamientos
                        if (_mEntrenamientos.isNotEmpty()) {
                            _isEntrenamiento.value = true
                        }
                    }

                }
            }
            "Partida Especial" -> {
                db.collection(evento).get().addOnSuccessListener { documents ->
                    documents?.forEach { document ->
                        var partida = Partida(
                            document.get("id") as String,
                            document.get("campo") as String,
                            document.get("jugadores") as List<String>,
                            document.get("fecha") as String,
                            document.get("isOficial") as Boolean
                        )
                        _mEspecial.add(partida)
                        _especial.value = _mEspecial
                        if (_mEspecial.isNotEmpty()) {
                            _isEspecialGame.value = true
                        }
                    }

                }
            }
        }

    }

    fun addGamerToGame(typeEvent: String, usuario: Usuario, partida: Partida) {
        var listaGamer = mutableListOf<String>()
        var myPartida = partida
        if (myPartida.jugadores.contains(usuario.nickName)) {
            usuario.nickName?.let { (myPartida.jugadores as MutableList<String>).add(it) }
            var isOficial: Boolean = contUsuarios(myPartida.jugadores)
            db.collection(typeEvent).document(partida.id).set(
                hashMapOf(
                    "id" to myPartida.id,
                    "campo" to myPartida.campo,
                    "jugadores" to listaGamer + partida.jugadores,
                    "fecha" to myPartida.fecha,
                    "isOficial" to isOficial
                )
            )
        }


    }

    fun contUsuarios(lista: List<String>): Boolean {
        var cont = 0
        lista.forEach {
            if (!it.contains("(inv)-")) {
                cont += 1
            }
        }
        return cont >= 5


    }

    fun removeGamerFromGame(typeEvent: String, usuario: Usuario, partida: Partida) {
        var isOficial: Boolean
        var myPartida = partida

        if (myPartida.jugadores.contains(usuario.nickName)) {
            (myPartida.jugadores as MutableList<String>).remove(usuario.nickName)
            isOficial = contUsuarios(myPartida.jugadores)
            db.collection(typeEvent).document(partida.id).set(
                hashMapOf(
                    "id" to myPartida.id,
                    "campo" to myPartida.campo,
                    "jugadores" to myPartida.jugadores,
                    "fecha" to myPartida.fecha,
                    "isOficial" to isOficial
                )
            )
        }

    }
}