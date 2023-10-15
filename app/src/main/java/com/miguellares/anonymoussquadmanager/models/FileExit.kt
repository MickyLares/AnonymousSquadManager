package com.miguellares.anonymoussquadmanager.models

data class FileExit(
    var nickName: String? = null,
    var isAdmin: Boolean = false,
    var email:String? = null,
    var password:String? = null,
    var partidas: List<String>? = null,
    var idFile: String? = null,
    var fecha: String? = null,
    var motivo: String? = null,
    var propuesto: String? = null,
    var votosSI: List<String> = emptyList(),
    var votosNO: List<String> = emptyList()
)
