package com.example.kiosko_model.models

data class Componentes(
    val id: Int,
    val titulo: String,
    val accesoDirecto: Int,
    val porcentaje: Int,
    val orden: Int,
    val desplegable: Int,
    val idModulo: String,
    val numeroHijos: Int,
    val padre: String?,
    val submodulos: List<Componentes>?,
    val componentes: List<Compuestos?>
)