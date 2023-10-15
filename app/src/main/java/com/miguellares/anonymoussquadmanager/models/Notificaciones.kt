package com.miguellares.anonymoussquadmanager.models

data class Notificaciones(
    var id:String? = null,
    var message:String? = null,
    var vistoPor: List<String>? = null
)
