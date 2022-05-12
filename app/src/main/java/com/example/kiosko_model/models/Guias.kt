
package com.example.kiosko_model.models

import com.example.kiosko_model.modelsimagenBoton.Compuestos

data class Guias(
    val id: Int,
    val titulo: String,
    val accesoDirecto: Int,
    val porcentaje: Int,
    val orden: Int,
    val desplegable: Int,
    val idModulo: String,
    val urlFondo : String,
    val numeroHijos: Int,
    val tipoGuia: String?,
    val padre: String?,
    val componentes: List<Compuestos?>
)