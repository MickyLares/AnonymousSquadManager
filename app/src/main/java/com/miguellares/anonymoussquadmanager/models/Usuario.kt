package com.miguellares.anonymoussquadmanager.models

data class Usuario(
    var nickName: String? = null,
    var isAdmin: Boolean = false,
    var email:String? = null,
    var password:String? = null,
    var partidas: List<String>? = null
)
