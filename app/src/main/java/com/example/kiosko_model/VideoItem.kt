package com.example.kiosko_model

import com.example.kiosko_model.modelsimagenBoton.Compuestos

class VideoItem {
    var icons: String ? = null
    var name: String ? = null
    var url: String ? = null

    constructor(icons: String?, name: String?, url: String?) {
        this.icons = icons
        this.name = name
        this.url = url
    }
}