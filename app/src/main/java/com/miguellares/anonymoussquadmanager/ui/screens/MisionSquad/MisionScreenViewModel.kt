package com.miguellares.anonymoussquadmanager.ui.screens.MisionSquad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.miguellares.anonymoussquadmanager.models.Mision
import com.miguellares.anonymoussquadmanager.utils.Utils

class MisionScreenViewModel : ViewModel() {
    private val _mision = MutableLiveData<Mision>()
    val mision: LiveData<Mision> = _mision
    private val db = Firebase.firestore

    fun getMisionSquad(){
        db.collection(Utils.MISION).document(Utils.MISION).get().addOnCompleteListener { task->
            if(task.isSuccessful){
                val mision = Mision(
                    task.result.get("title") as String,
                    task.result.get("message") as String
                )
                _mision.value = mision
            }
        }
    }
}