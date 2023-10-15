package com.miguellares.anonymoussquadmanager.models

data class ModelPartida (
    var typePartida: String = "",
    var listPartida: List<Partida> = emptyList()
)