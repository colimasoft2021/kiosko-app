package com.example.kiosko_model.models

data class Componentes(
    val id: Int,
    val titulo: String,
    val accesoDirecto: Int,
    val orden: Int,
    val desplegable: Int,
    val idModulo: String,
    val padre: String?,
    val componentes: List<Compuestos>
)