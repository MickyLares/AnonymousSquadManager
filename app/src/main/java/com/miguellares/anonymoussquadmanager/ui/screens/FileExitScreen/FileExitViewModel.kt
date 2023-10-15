package com.miguellares.anonymoussquadmanager.ui.screens.FileExitScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.miguellares.anonymoussquadmanager.models.FileExit
import com.miguellares.anonymoussquadmanager.models.Usuario
import com.miguellares.anonymoussquadmanager.utils.Utils

class FileExitViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _FileExit = MutableLiveData<List<FileExit>>()
    val FileExit: LiveData<List<FileExit>> = _FileExit
    private val _openDialog = MutableLiveData<Boolean>()
    val openDialog: LiveData<Boolean> = _openDialog
    private val _myFile = MutableLiveData<FileExit>()
    val myFile: LiveData<FileExit> = _myFile
    private val _voted = MutableLiveData<Boolean>()
    val voted: LiveData<Boolean> = _voted
    val auth = Firebase.auth

    fun removeUserFromDB(email: String, home: () -> Unit) {

        db.collection(Utils.USUARIO).document(email).delete().addOnCompleteListener {
            if (it.isSuccessful) {
                home()
            }
        }

    }

    fun checkVote(fileExit: FileExit, usuario: Usuario) {
        val myUsuario = usuario
        val myFileExit = fileExit
        _voted.value =
            (myFileExit.votosNO.contains(myUsuario.email) || myFileExit.votosSI.contains(myUsuario.email))

    }

    fun voteSi(fileExit: FileExit, usuario: Usuario) {
        fileExit.email?.let {
            val lista: MutableList<String> = fileExit.votosSI as MutableList<String>
            lista.add("${usuario.email}")
            db.collection(Utils.FILE_EXIT).document(it).update("votosSI", lista)
        }
        _voted.value = true
    }

    fun voteNo(fileExit: FileExit, usuario: Usuario) {
        val lista: MutableList<String> = fileExit.votosNO as MutableList<String>
        lista.add("${usuario.email}")
        fileExit.email?.let {
            db.collection(Utils.FILE_EXIT).document(it).update("votosNO", lista)
        }
        _voted.value = true
    }

    fun openDialog(value: Boolean, fileExit: FileExit) {
        _openDialog.value = value
        _myFile.value = fileExit
    }

    fun getFileExit() {
        var lista = mutableListOf<FileExit>()
        db.collection(Utils.FILE_EXIT).get().addOnCompleteListener { Alltask ->
            if (Alltask.isSuccessful && Alltask.result != null) {
                Alltask.result.documents.forEach { task ->
                    val file = FileExit(
                        task.get("nickName") as String,
                        task.get("admin") as Boolean,
                        task.get("email") as String,
                        task.get("password") as String,
                        task.get("partidas") as List<String>,
                        task.get("idFile") as String,
                        task.get("fecha") as String,
                        task.get("motivo") as String,
                        task.get("propuesto") as String,
                        task.get("votosSI") as List<String>,
                        task.get("votosNO") as List<String>
                    )
                    lista.add(file)
                }

                _FileExit.value = lista
            }
        }

    }
}