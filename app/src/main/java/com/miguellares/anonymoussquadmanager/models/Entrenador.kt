package com.miguellares.anonymoussquadmanager.models

data class Entrenador(
    var email: String? = null,
    var nickName: String? = null,
    var idpartida: List<String>? = null,
    var fecha: List<String>? = null
)