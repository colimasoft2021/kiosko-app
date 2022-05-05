package com.example.kiosko_model

import com.example.kiosko_model.modelsimagenBoton.Compuestos

class ItemDataGuias {
    var icons: String ? = null
    var name: String ? = null
    var componentes:  List<Compuestos?>? = null

    constructor(icons: String?, name: String?, componentes: List<Compuestos?>) {
        this.icons = icons
        this.name = name
        this.componentes = componentes
    }
}