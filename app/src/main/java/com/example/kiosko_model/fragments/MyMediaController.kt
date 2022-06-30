package com.example.kiosko_model.fragments

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.MediaController

class MyMediaController : MediaController {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, useFastForward: Boolean) : super(context, useFastForward) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    private var _isShowing = false
    override fun isShowing(): Boolean {
        return _isShowing
    }

    override fun show() {
        super.show()
        _isShowing = true
        val parent = this.parent as ViewGroup
        parent.visibility = VISIBLE
    }

    override fun hide() {
        super.hide()
        _isShowing = false
        val parent = this.parent as ViewGroup
        parent.visibility = GONE
    }
}