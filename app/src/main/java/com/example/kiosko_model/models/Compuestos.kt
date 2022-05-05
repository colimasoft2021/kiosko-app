package com.example.kiosko_model.modelsimagenBoton

import android.net.Uri
import com.example.kiosko_model.models.Desplazantes
import com.example.kiosko_model.models.idModuloNavigation
import java.net.URL

data class Compuestos (
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
    val idModuloNavigation : idModuloNavigation?,
    val desplazantes: List<Desplazantes>
    )
