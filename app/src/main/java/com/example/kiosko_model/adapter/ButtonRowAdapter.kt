package com.example.kiosko_model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.kiosko_model.R
import com.example.kiosko_model.models.Componentes
import com.example.kiosko_model.models.CompuestosViewModel


class ButtonRowAdapter :
    RecyclerView.Adapter<ButtonRowAdapter.ViewHolder>() {

    private var dataSet = emptyList<Componentes>()
    var position: Int = 3000

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.button_row_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val boton = viewHolder.itemView.rootView.findViewById<Button>(R.id.botones)
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        boton.text = dataSet[position].titulo
        boton.setOnClickListener {


        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun p(newPosition: Int){
        position = newPosition
    }
    fun returnPosition(): Int {
        return if (position!=3000) {
            position

        }else{
            3000
        }

    }

    fun setData(newList: List<Componentes>){
        dataSet = newList
        notifyDataSetChanged()
    }

}