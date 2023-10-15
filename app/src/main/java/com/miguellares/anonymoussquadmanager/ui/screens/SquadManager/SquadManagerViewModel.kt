package com.miguellares.anonymoussquadmanager.ui.screens.SquadManager

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.miguellares.anonymoussquadmanager.models.FileExit
import com.miguellares.anonymoussquadmanager.models.Partida
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

class SquadManagerViewModel : ViewModel() {
    private val _listaUsuarios = MutableLiveData<List<Usuario>>()
    val listaUsuarios: LiveData<List<Usuario>> = _listaUsuarios
    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val _isOpenDialog = MutableLiveData<Boolean>()
    val isOpenDialog: LiveData<Boolean> = _isOpenDialog
    private val _cantidadPartidas = MutableLiveData<Int>()
    val cantidadPartidas: LiveData<Int> = _cantidadPartidas
    private val _listadoPartidas = MutableLiveData<List<Partida>>()
    val listadoPartida: LiveData<List<Partida>> = _listadoPartidas
    private val _onClickExitFile = MutableLiveData<Boolean>()
    val onClickExitFile: LiveData<Boolean> = _onClickExitFile

    private val _isValidNikName = MutableLiveData<Boolean>()
    val isValidNickname: LiveData<Boolean> = _isValidNikName
    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName

    private val _isValidPassword = MutableLiveData<Boolean>()
    val isValidPassword: LiveData<Boolean> = _isValidPassword
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isValidEmail = MutableLiveData<Boolean>()
    val isValidEmail: LiveData<Boolean> = _isValidEmail
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _isValidMessage = MutableLiveData<Boolean>()
    val isValidMessage: LiveData<Boolean> = _isValidMessage

    private val _motivo = MutableLiveData<String>()
    val motivo: LiveData<String> = _motivo
    private val _isValidMotivo = MutableLiveData<Boolean>()
    val isValidMotivo: LiveData<Boolean> = _isValidMotivo

    private val _openNotificationUI = MutableLiveData<Boolean>()
    val openNotificationUI: LiveData<Boolean> = _openNotificationUI

    private val _usuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario> = _usuario

    private val _entrenamientos = MutableLiveData<List<Partida>>()
    val entrenamientos: LiveData<List<Partida>> = _entrenamientos

    private val _partidasEspeciales = MutableLiveData<List<Partida>>()
    val partidasEspeciales: LiveData<List<Partida>> = _partidasEspeciales

    private val _asistencia = MutableLiveData<Int>()
    val asistencia: LiveData<Int> = _asistencia

    private val _userToRemove = MutableLiveData<Usuario>()
    val userToRemove: LiveData<Usuario> = _userToRemove

    private val _openExitFileDialog = MutableLiveData<Boolean>()
    val openExitFileDialog: LiveData<Boolean> = _openExitFileDialog

    fun openDialogExitFile(value: Boolean){
        _openExitFileDialog.value = value
    }

    fun onClickExitFile(value: Boolean) {
        _onClickExitFile.value = value
    }


    fun openFileToRemoveUser(usuario: Usuario, message: String, propuestoPor: String) {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())
        var fileExit = FileExit(
            nickName = usuario.nickName,
            isAdmin = usuario.isAdmin,
            email = usuario.email,
            password = usuario.password,
            partidas = usuario.partidas,
            idFile = "exp_${usuario.email}",
            fecha = currentDate,
            motivo = message,
            propuesto = propuestoPor,
            votosSI = emptyList(),
            votosNO = emptyList()
        )
        usuario.email?.let {
            db.collection(Utils.FILE_EXIT).document(it).set(
                fileExit
            )
        }
    }

    fun onClickOpenNotificationUI(value: Boolean) {
        _openNotificationUI.value = value
    }

    fun updateAdmin(myState: Boolean, usuario: Usuario) {
        usuario.email?.let { email ->
            db.collection(Utils.USUARIO).document(email).update("isAdmin", myState)
        }
    }

    fun crearUsuario(email: String, password: String, nickName: String) {
        _usuario.value = Usuario(
            nickName = nickName,
            false,
            email = email,
            password = password,
            partidas = emptyList()
        )
        usuario.value?.let { addNewGamer(it) }
    }

    fun sendNotificacion(message: String) {
        db.collection(Utils.NOTIFICACIONES).document(Utils.AVISO).set(
            hashMapOf(
                "id" to Utils.AVISO,
                "message" to message,
                "vistoPor" to emptyList<String>()
            )
        )
    }

    private fun isValidMessage(message: String) {
        _isValidMessage.value = message.isNotEmpty()
    }

    private fun isValidMotivo(motivo: String) {
        _isValidMotivo.value = motivo.isNotEmpty()
    }

    fun onChangeMotivo(motivo: String) {
        isValidMotivo(motivo)
        if (_isValidMotivo.value == true) {
            _motivo.value = motivo
        }
    }

    fun onChangeMessage(message: String) {
        isValidMessage(message)
        if (isValidMessage.value == true) {
            _message.value = message
        }
    }

    private fun isValidNickname(nickName: String) {
        _isValidNikName.value = nickName.isNotEmpty()
    }

    fun onChangeNicknameRegistro(nickName: String) {
        _nickName.value = nickName
        isValidNickname(nickName)
    }

    private fun isValidPassword(password: String) {
        _isValidPassword.value = password.length >= 9
    }

    fun onPasswordRegistroChange(password: String) {
        _password.value = password
        isValidPassword(password)
    }

    private fun isValidEmail(email: String) {
        _isValidEmail.value = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun onChangeEmailRegistro(email: String) {
        _email.value = email
        isValidEmail(email)
    }

    fun getAllGamesManager(evento: String, showError: () -> Unit) {

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
                        _listadoPartidas.value = _mPartidas

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
                        _partidasEspeciales.value = _mEspecial

                    }

                }
            }
        }

    }

    fun getAllUser() {
        db.collection("Usuario").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var listaP = mutableListOf<Usuario>()
                task.result.documents.forEach { document ->
                    val usuario = Usuario(
                        document.get("nickName") as String,
                        document.get("isAdmin") as Boolean,
                        document.get("email") as String,
                        document.get("password") as String,
                        document.get("partidas") as List<String>
                    )
                    listaP.add(usuario)
                    _listaUsuarios.value = listaP
                }
            }
        }
    }

    fun contadorPartidasOficiales(
        partidas: List<Partida>,
        entrenamientos: List<Partida>,
        partidasEspeciales: List<Partida>,
        usuario: Usuario
    ) {
        var listaresult = mutableListOf<Partida>()
        partidas.forEach {
            if (it.isOficial && !listaresult.contains(it)) {
                listaresult.add(it)
            }
        }
        entrenamientos.forEach {
            if (it.isOficial && !listaresult.contains(it)) {
                listaresult.add(it)
            }
        }
        partidasEspeciales.forEach {
            if (it.isOficial && !listaresult.contains(it)) {
                listaresult.add(it)
            }
        }
        _cantidadPartidas.value = listaresult.size
        var contAsistencia = 0
        listaresult.forEach {
            if (it.jugadores.contains(usuario.nickName) && it.isOficial) {
                contAsistencia += 1
            }
        }
        _asistencia.value = contAsistencia
    }

    private fun addNewGamer(usuario: Usuario) {
        usuario.email?.let { email ->
            usuario.password?.let { password ->
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        usuario.email?.let { mEmail ->
                            db.collection("Usuario").document(mEmail).set(
                                hashMapOf(
                                    "email" to usuario.email,
                                    "nickName" to usuario.nickName,
                                    "password" to usuario.password,
                                    "partidas" to usuario.partidas,
                                    "isAdmin" to usuario.isAdmin
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun onClickShowRegistro(value: Boolean) {
        _isOpenDialog.value = value
    }

    fun userToRemove(usuario: Usuario) {
        _userToRemove.value = usuario
    }
}

