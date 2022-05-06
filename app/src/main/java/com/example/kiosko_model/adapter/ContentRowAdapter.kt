package com.example.kiosko_model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.kiosko_model.R
import com.example.kiosko_model.modelsimagenBoton.Compuestos


class ContentRowAdapter(context: Context?) :
    RecyclerView.Adapter<ContentRowAdapter.ViewHolder>() {

    private var dataSet = emptyList<Compuestos?>()
    private val context = context

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.content_row_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val subtitulo = viewHolder.itemView.rootView.findViewById<TextView>(R.id.subtitulo)
        val texto = viewHolder.itemView.rootView.findViewById<TextView>(R.id.texto)
        val imagen = viewHolder.itemView.rootView.findViewById<ImageView>(R.id.imagen)
        val pieImagen = viewHolder.itemView.rootView.findViewById<TextView>(R.id.pieImagen)
        val BannerInfo = viewHolder.itemView.rootView.findViewById<TextView>(R.id.BannerInfo)
        val button = viewHolder.itemView.rootView.findViewById<Button>(R.id.button)
        val imagenView = viewHolder.itemView.rootView.findViewById<ConstraintLayout>(R.id.imagenView)

        val imagenDesplazante = viewHolder.itemView.rootView.findViewById<ImageView>(R.id.imagenDesplazante)
        val tituloImagenDesplazante = viewHolder.itemView.rootView.findViewById<TextView>(R.id.tituloImagenDesplazante)
        val textoImagenDesplazante = viewHolder.itemView.rootView.findViewById<TextView>(R.id.textoImagenDesplazante)
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        when(dataSet[position].tipoComponente){
//
//            "subtitulo" -> {
//                subtitulo.text = dataSet[position].subtitulo
//            }
//
//            "banner-informativo" -> {
//                BannerInfo.text = dataSet[position].descripcion
//
////                BannerInfo.setBackgroundColor(Color.parseColor(dataSet[position].backgroundColor)   )
//            }
//
//            "carrucel" -> subtitulo.text = dataSet[position].tipoComponente
//
//            "texto" -> texto.text = dataSet[position].descripcion
//
//            "desplazante-texto-imagen" -> {
//                dataSet[position].desplazantes.forEach {
//
//                    imagenDesplazante.load(it.url) {
//                        placeholder(R.drawable.loading_animation)
//                        error(R.drawable.ic_broken_image)
//                        tituloImagenDesplazante.text = it.titulo
//                        textoImagenDesplazante.text = it.texto
//
//                    }
//
//
//                }
//
//                subtitulo.text = dataSet[position].tipoComponente
//            }
//
//            "texto-imagen" ->  subtitulo.text = dataSet[position].tipoComponente
//
//            "enlace" -> { button.text = dataSet[position].titulo
//                button.setOnClickListener {
//
//                    val url = dataSet[position].url
//                    val intent = Intent(Intent.ACTION_VIEW)
//                    intent.data = Uri.parse(url)
//                    context!!.startActivity(intent)
//
//
//                }
//
//            }
//            "imagen" -> {  imagen.load(dataSet[position].url) {
//                    placeholder(R.drawable.loading_animation)
//                    error(R.drawable.ic_broken_image)
//                    pieImagen.text = dataSet[position].descripcion
//                }
//                imagenView.addView(imagen)
//            }
//            "video" -> {
//                button.text = "Click para ver"
//                button.setOnClickListener {
//
//                    val url = dataSet[position].url
//                    val intent = Intent(Intent.ACTION_VIEW)
//                    intent.data = Uri.parse(url)
//                    context!!.startActivity(intent)
//                }
//            }
//        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(  ) = dataSet.size

    fun setData(newList: List<Compuestos?>){
        dataSet = newList
        notifyDataSetChanged()
    }

}