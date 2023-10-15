package com.miguellares.anonymoussquadmanager.models

data class Partida(
    var id:String = "",
    var campo:String = "",
    var jugadores:List<String> = emptyList(),
    var fecha:String = "",
    var isOficial:Boolean = false
)
