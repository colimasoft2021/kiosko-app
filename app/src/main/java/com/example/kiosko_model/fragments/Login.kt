package com.example.kiosko_model.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.MainActivity
import com.example.kiosko_model.PopUps.LoadingDialog
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.LoginBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class Login : Fragment() {

//    private val viewModel: LoginModel by viewModels()

    private lateinit var viewLogModel: LoginViewModel
    private lateinit var viewLogRegModel: LoginRegistroViewModel

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
        var loginViewModelState = LoginUiState(isLoading = false, errorMessage = null, isUserLoggedIn = false)



        binding.buttonSecond.setOnClickListener {
            //val loading = LoadingDialog(context as Activity)
           if (usertexto.text!!.isNotEmpty()&&passtexto.text!!.isNotEmpty()) {
                binding.buttonSecond.isClickable = false
               //loading.displayLoading()
               val myPost = Post(usertexto.text.toString(), passtexto.text.toString())

               viewLogModel.changeState(loginViewModelState)
                if(viewLogModel.uiState.value?.isUserLoggedIn == false) {
                    viewLogModel.pushPost(myPost)
                    viewLogModel.myResponse.observe(viewLifecycleOwner,
                        Observer { response ->
                            Log.d("respuesta",response.body().toString())
                            Log.d("respuesta completa", response.toString())

                            if (response.isSuccessful) {
                                if(response.body().isNullOrEmpty()){
                                    //loading.hideLoading()
                                    binding.buttonSecond.isClickable = true
                                    showMaterialDialog(
                                        "Error",
                                        "Usuario y/o contraseña incorrectos."
                                    )
                                    //viewLogModel.myResponse.removeObservers(viewLifecycleOwner)
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

                                        //loading.hideLoading()
                                        findNavController().navigate(R.id.action_login_to_remember)
                                    })
                                    usertexto.text = ""
                                    passtexto.text = ""
                                }
                                //viewLogModel.myResponse.removeObservers(viewLifecycleOwner)
                            } else {
                                //loading.hideLoading()
                                binding.buttonSecond.isClickable = true
                                showMaterialDialog("Error", "Ha ocurrido algún error, inténtelo de nuevo más tarde.")
                                usertexto.text = ""
                                passtexto.text = ""
                            }
                            viewLogModel.myResponse.removeObservers(viewLifecycleOwner)
                        })

                }else{
                    binding.buttonSecond.isClickable = true
                    //loading.hideLoading()
                }
           }else{
               binding.buttonSecond.isClickable = true
               //loading.hideLoading()
               showMaterialDialog("Error", "El usuario y contraseña son requeridos.")
           }

        }
    }



}