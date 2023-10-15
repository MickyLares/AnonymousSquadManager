package com.miguellares.anonymoussquadmanager.ui.screens.PerfilScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.navigation.AppScreen
import com.miguellares.anonymoussquadmanager.utils.Utils

class PerfilScreenViewModel : ViewModel() {
    private val _nickName = MutableLiveData<String>()
    private val db = Firebase.firestore
    private val auth = Firebase.auth
    val nickName: LiveData<String> = _nickName
    private val _isValidNickName = MutableLiveData<Boolean>()
    val isValidNickName: LiveData<Boolean> = _isValidNickName
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    private val _isValidPassword = MutableLiveData<Boolean>()
    val isValidPassword: LiveData<Boolean> = _isValidPassword
    private val _password1 = MutableLiveData<String>()
    val password1: LiveData<String> = _password1
    private val _nickName1 = MutableLiveData<String>()
    val nickName1: LiveData<String> = _nickName1
    private val _reinicio = MutableLiveData<Boolean>()
    val reinicio: LiveData<Boolean> = _reinicio
    private val _tiempo = MutableLiveData<Long>()
    val tiempo: LiveData<Long> = _tiempo

    private val _onClickEdit = MutableLiveData<Boolean>()
    val onClickEdit: LiveData<Boolean> = _onClickEdit

    fun changeTime(tiempo: Long) {
        _tiempo.value = tiempo
    }

    fun editUserData(usuario: Usuario, nickName: String, password: String) {
        val mUsuario = auth.currentUser
        mUsuario?.updatePassword(password)
        usuario.email?.let { email ->
            db.collection(Utils.USUARIO).document(email).set(
                hashMapOf(
                    "email" to email,
                    "isAdmin" to usuario.isAdmin,
                    "nickName" to nickName,
                    "partidas" to usuario.partidas,
                    "password" to password
                )
            )
            _reinicio.value = true
        }
    }

    fun logoutUserPerfil(navController: NavController) {
        auth.signOut()
        navController.navigate(AppScreen.LoginScreen.route)
    }

    fun onClickEdit(value: Boolean) {
        _onClickEdit.value = value
    }

    private fun isValidPassword(password: String) {
        _isValidPassword.value = password.length >= Utils.LENGTH_PASSWORD
    }

    private fun isValidNickName(nickName: String) {
        _isValidNickName.value = nickName.isNotEmpty()
    }

    fun editNickName() {
        _nickName.value = ""
    }

    fun setDataUsuario(usuario: Usuario) {
        _nickName.value = usuario.nickName
        _password.value = usuario.password
    }

    fun onPasswordChange(password: String) {
        isValidPassword(password)
        _password1.value = password

    }

    fun onNickNameChanged(
        nickName: String,
    ) {
        _nickName1.value = nickName
        isValidNickName(nickName)
    }
}

