package com.example.kiosko_model.models

import com.example.kiosko_model.modelsimagenBoton.Compuestos

data class Videos(
    val id: Int,
    val padre: String,
    val tipoComponente: String,
    val url: String?,
    val descripcion: String?,
    val backgroundColor: String?,
    val agregarFondo: String?,
    val titulo: String?,
    val subtitulo: String?,
    val orden: Int,
    val urlDos: String?,
    val urlTres: String?,
    val idModulo: Int,
    val idModuloNavigation : idModuloNavigation,
    val desplazantes: List<Desplazantes>
)