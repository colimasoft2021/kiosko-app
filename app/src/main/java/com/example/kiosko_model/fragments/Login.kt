package com.example.kiosko_model.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.AppOpsManager
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.MainActivity
import com.example.kiosko_model.PopUps.LoadingScreen
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.LoginBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class Login : Fragment() {

//    private val viewModel: LoginModel by viewModels()

    private lateinit var viewLogModel: LoginViewModel
    private lateinit var viewLogRegModel: LoginRegistroViewModel
    lateinit var loading: Dialog



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
        return binding.root

    }

    fun showMaterialDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton("Ok") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showLoading() {
        loading = Dialog(requireContext())
        loading.setContentView(R.layout.activity_loading_spash_screen)

        loading.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loading.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {                                                                    
        super.onViewCreated(view, savedInstanceState)
        val repository = Repository()
        val viewModelFactory = LoginViewModelFactory(repository)
        val viewModelRegistroFactory = LoginRegistroViewModelFactory(repository)

        val layout = binding.layoutLogin

        val  usertexto: TextView = binding.usuariotxt
        val  passtexto: TextView = binding.contrasenatxt
        viewLogModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        viewLogRegModel = ViewModelProvider(this, viewModelRegistroFactory)[LoginRegistroViewModel::class.java]

        val mContext = this.context

        layout.setOnClickListener {
            (activity as MainActivity?)?.hideSystemUI()

            val manager = mContext?.getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            manager
                ?.hideSoftInputFromWindow(
                    view.windowToken, 0)

        }

        binding.buttonSecond.setOnClickListener {
           if (usertexto.text!!.isNotEmpty()&&passtexto.text!!.isNotEmpty()) {
               showLoading()
               val myPost = Post(usertexto.text.toString(), passtexto.text.toString())

               viewLogModel.pushPost(myPost)
                            viewLogModel.myResponse.observe(viewLifecycleOwner,
                                Observer { response ->
                                    Log.d("respuesta",response.body().toString())
                                    Log.d("respuesta completa", response.toString())

                                    if (response.isSuccessful) {
                                        if(response.body().isNullOrEmpty()){
                                            showMaterialDialog("Error", "Usuario y/o contraseña incorrectos.")
                                            loading.dismiss()
                                        } else {
                                            showMaterialDialog("Bienvenido", response.body()?.get(0)?.nombre_Completo.toString())
                                            val name =
                                                response.body()?.get(0)?.nombre_Completo.toString()
                                            val UserId = response.body()?.get(0)?.iD_Usuario

                                            val PostRegistro =
                                                PostRegistro(UserId!!, name, "Empleado")

                                            viewLogRegModel.pushPostRegistro(PostRegistro)
                                            viewLogRegModel.myResponse.observe(viewLifecycleOwner, Observer { r ->
                                                Log.d("RNSuccesfullCode", id.toString()+""+name )
                                                val sharedPref = this.requireActivity()
                                                    .getSharedPreferences("UsD", Context.MODE_PRIVATE)
                                                val editor = sharedPref.edit()
                                                editor.putString("userName", name)
                                                editor.putString("userID",UserId.toString())
                                                editor.putString("id", r.body()?.id.toString())
                                                editor.apply()
                                                Log.d("ErrorASDASDASDAASDASD#",  r.body()?.id.toString())

                                                loading.dismiss()
                                                findNavController().navigate(R.id.action_login_to_remember)
                                            })
                                            usertexto.text = ""
                                            passtexto.text = ""
                                        }
                                    } else {
                                        showMaterialDialog("Error", "Ha ocurrido algún error, inténtelo de nuevo más tarde.")
                                        loading.dismiss()
                                        usertexto.text = ""
                                        passtexto.text = ""
                                    }
                                })

           }else{
               showMaterialDialog("Error", "El usuario y contraseña son requeridos.")
           }

        }
    }



}