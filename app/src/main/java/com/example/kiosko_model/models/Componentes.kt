package com.example.kiosko_model.models

import com.example.kiosko_model.modelsimagenBoton.Compuestos

data class Componentes(
    val id: Int,
    val titulo: String,
    val accesoDirecto: Int,
    val porcentaje: Int,
    val orden: Int,
    val desplegable: Int,
    val idModulo: String,
    val numeroHijos: Int,
    val url: String?,
    val urlFondo:String?,
    val backgroundColor:String?,
    val padre: String?,
    val submodulos: List<Componentes2>?,
    val componentes: List<Compuestos?>
)