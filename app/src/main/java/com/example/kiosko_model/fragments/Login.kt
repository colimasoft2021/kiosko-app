package com.example.kiosko_model.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.R
import com.example.kiosko_model.repository.Repository
import com.example.kiosko_model.databinding.LoginBinding
import com.example.kiosko_model.models.LoginViewModel
import com.example.kiosko_model.models.LoginViewModelFactory
import com.example.kiosko_model.models.Post
import okhttp3.internal.http2.ErrorCode
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import kotlin.Exception
import android.content.SharedPreferences





class Login : Fragment() {

//    private val viewModel: LoginModel by viewModels()

    private lateinit var viewLogModel: LoginViewModel

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */

    private var _binding: LoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = LoginBinding.inflate(inflater, container, false)
//        binding.lifecycleOwner = this
//        binding.viewModel = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {                                                                    
        super.onViewCreated(view, savedInstanceState)
        val repository = Repository()
        val viewModelFactory = LoginViewModelFactory(repository)

        val  usertexto: TextView = binding.usuariotxt
        val  passtexto: TextView = binding.contrasenatxt

        binding.buttonSecond.setOnClickListener {

            findNavController().navigate(R.id.action_login_to_remember)

            try {
                try{

                    val myPost = Post(usertexto.text.toString(), passtexto.text.toString())

                    viewLogModel =
                        ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
                    viewLogModel.pushPost(myPost)

                    viewLogModel.myResponse.observe(this, Observer { response ->

                        if (response.isSuccessful) {
                            try {

                                Toast.makeText(context,
                                    "Bienvenido ${response.body()?.get(0)?.nombre_Completo}",
                                    Toast.LENGTH_SHORT).show()

                                val name = response.body()?.get(0)?.nombre_Completo.toString()
                                val id = response.body()?.get(0)?.iD_Usuario.toString()

                                val sharedPref = this.activity!!.getSharedPreferences("UsD",Context.MODE_PRIVATE)
                                val editor = sharedPref.edit()
                                    editor.putString("userName", name )
                                    editor.putString("userID", id )
                                    editor.apply()

                                findNavController().navigate(R.id.action_login_to_remember)

                                usertexto.text = ""
                                passtexto.text = ""

                            } catch (e: HttpException) {


                                Toast.makeText(context,
                                    response.code().toString(),
                                    Toast.LENGTH_SHORT)
                                    .show()
                                Log.d("Response Code Exception", response.code().toString())


                            }
                        } else {
                            Toast.makeText(context, response.code().toString(), Toast.LENGTH_SHORT)
                                .show()
                            Log.d("RNSuccesfullCode", response.code().toString())

                        }


                    })
                } catch (e: TimeoutException){
                    Log.d("TimeOutEx", e.toString())

                }
            }catch (e:SocketTimeoutException){
                Log.d("SocketTimeOutEx", e.toString())

            }

        }
    }

}