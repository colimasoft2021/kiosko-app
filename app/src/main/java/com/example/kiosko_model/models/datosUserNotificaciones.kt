
package com.example.kiosko_model.models

import com.example.kiosko_model.modelsimagenBoton.Compuestos

data class datosUserNotificaciones(
    val nombre : String,
    val idUsuarioKiosko : Int,
    val modulosInactivos : List<Modulo>
)