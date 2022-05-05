package com.example.kiosko_model.fragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.*
import com.example.kiosko_model.databinding.FragmentVideosBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository
import retrofit2.Response
import java.util.concurrent.TimeoutException

private lateinit var viewModel: VideosViewModel

class VideosFragment : Fragment(), AdapterView.OnItemClickListener {


    private var gridView:GridView? = null
    private var List: List<VideoItem> ? = null
    private var videosAdapter: VideosAdapter? = null


    private var _binding: FragmentVideosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.llTituloVideo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.liston_4,0, R.drawable.liston_4,0)

        val col = Color.parseColor("#000000")
        val rad = 20//radius will be 5px
        val strk = 5
        val gD = GradientDrawable()
        gD.setColor(col)
        gD.cornerRadius = rad.toFloat()
        gD.setStroke(strk, col)

        val buton = Button(context)
        buton.background = gD
        buton.setTextColor(Color.WHITE)
        buton.text =" Acceso directo "
        buton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back_button,0,0,0)
        buton.setOnClickListener {

            findNavController().navigate(R.id.action_videosFragment_to_accesoDirectoFragment)

        }
        binding.bButonVideo.addView(buton)


        val repository = Repository()
        val viewModelFactory = VideosViewModelFactory(repository)


        viewModel = ViewModelProvider(this, viewModelFactory)[VideosViewModel::class.java]


        try {
            viewModel.getVideos()
            viewModel.videosResponse.observe(viewLifecycleOwner) { response ->

                gridView = binding.ListaVideo
                List = ArrayList()
                List = setDataList(response)
                videosAdapter = VideosAdapter(requireContext(), List as List<VideoItem>)
                gridView?.adapter = videosAdapter
                gridView?.onItemClickListener = this



            }
        } catch (e: TimeoutException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()

        }
    }

    private fun setDataList(list: Response<List<Videos>>) : ArrayList<VideoItem>{

        var array:ArrayList<VideoItem> = ArrayList()
        var index = 0

        var arrayColors:ArrayList<String> = ArrayList()
        arrayColors.add("#FC4C02")
        arrayColors.add("#008BCE")
        arrayColors.add("#DA291C")
        arrayColors.add("#43B02A")
        arrayColors.add("#D9027D")
        arrayColors.add("#003DA5")
        arrayColors.add("#FFFFFF")



        list.body()!!.forEach{

            val color : String

            if(index <= arrayColors.size ){
                color = arrayColors.get(index)
                index++
            }else{
                index = 0
                color = arrayColors.get(index)
            }
            array.add(VideoItem(color,it.descripcion,it.url))
        }
        return array
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        var item:VideoItem = List?.get(p2)!!
        (activity as Home?) ?.PopUpComponenteVideo(item.name,item.url,mensajeInicial = false)
//        Toast.makeText(requireContext(), "workkk+${item.name}+${item.icons}",Toast.LENGTH_LONG).show()

    }


}