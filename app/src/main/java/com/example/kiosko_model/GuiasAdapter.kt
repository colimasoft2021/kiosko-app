package com.example.kiosko_model

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton


class GuiasAdapter(var context: Context, var arrayList: List<ItemDataGuias>) : BaseAdapter() {


    override fun getItem(p0: Int): Any {
        return arrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view:View = View.inflate(context, R.layout.card_item,null)

//        var icon: ImageView = view.findViewById(R.id.idCourse)
//        var names: TextView = view.findViewById(R.id.TxtCourse)
        var names: TextView = view.findViewById(R.id.txtGuias)
     var boton: ImageView = view.findViewById(R.id.idIVcourse)



        var guiasItems: ItemDataGuias = arrayList.get(p0)
        val colorHEX = Color.parseColor(guiasItems.icons)

        val color = colorHEX
        val radius = 10
        val strokeWidth = 5
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(color)
        gradientDrawable.cornerRadius = radius.toFloat()
        gradientDrawable.setStroke(strokeWidth, color)


        boton.background = gradientDrawable
        names.text = guiasItems.name

        return view

    }
}