package com.example.kiosko_model.models

import com.example.kiosko_model.modelsimagenBoton.Compuestos

data class Componentes2(
    val id: Int,
    val titulo: String,
    val accesoDirecto: Int,
    val porcentaje: Int,
    val orden: Int,
    val desplegable: Int,
    val idModulo: String,
    val numeroHijos: Int,
    val urlFondo:String?,
    val padre: String?,
    val submodulos: List<Componentes>?,
    val componentes: List<Compuestos?>
)