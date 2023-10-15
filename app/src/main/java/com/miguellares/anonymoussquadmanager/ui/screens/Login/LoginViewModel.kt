package com.miguellares.anonymoussquadmanager.ui.screens.Login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.utils.Utils
import com.miguellares.anonymoussquadmanager.utils.Utils.Companion.LENGTH_PASSWORD
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class LoginViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    private val _usuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario> = _usuario
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    private val _isValidEmail = MutableLiveData(false)
    val isValidEmail: LiveData<Boolean> = _isValidEmail
    private val _isValidPassword = MutableLiveData(false)
    val isValidPassword: LiveData<Boolean> = _isValidPassword
    private val _onClickForgetPassword = MutableLiveData<Boolean>()
    val onClickForgetPassword: LiveData<Boolean> = _onClickForgetPassword


    fun onCLickForgetPassword(value: Boolean) {
        _onClickForgetPassword.value = value
    }

    fun sendEmailWithLinkPassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onCLickForgetPassword(false)
            }
        }
    }

    fun getUserbyEmail(email: String?, password: String, error: () -> Unit) {
        db.collection(Utils.USUARIO).document(email!!).update("password", password)
        db.collection(Utils.USUARIO).document(email).get().addOnSuccessListener { document ->
            if (document != null) {
                var usuario = Usuario(
                    document.get("nickName") as String,
                    document.get("isAdmin") as Boolean,
                    document.get("email") as String,
                    document.get("password") as String,
                    document.get("partidas") as List<String>
                )
                _usuario.value = usuario
            }else{
                error()
            }
        }

    }

    fun signWithEmailandPassword(
        email: String,
        password: String,
        home: () -> Unit,
        showErrorLogin: () -> Unit
    ) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _loading.value = true
                        home()
                        _loading.value = false
                    } else {
                        _loading.value = true
                        showErrorLogin()
                    }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
        }

    private fun isValidEmail(email: String) {
        _isValidEmail.value = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun onEmailLoginChanged(email: String) {
        _email.value = email
        isValidEmail(email)
    }

    private fun isValidPassword(password: String) {
        _isValidPassword.value = password.length >= LENGTH_PASSWORD
    }

    fun onPasswordLoginChanged(password: String) {
        _password.value = password
        isValidPassword(password)
    }

}
