package com.example.kiosko_model.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.Home
import com.example.kiosko_model.MainActivity
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.LoginBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout


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
//        (activity as MainActivity?)?.PopUpLoading(false)

        clickReady()

        val repository = Repository()
        val viewModelFactory = LoginViewModelFactory(repository)
        val viewModelRegistroFactory = LoginRegistroViewModelFactory(repository)

        val layout = binding.layoutLogin

        val  usertexto: TextView = binding.usuariotxt
        val  passtexto: TextView = binding.contrasenatxt
        val  usuariooo: TextInputLayout = binding.usuario
        val  pass: TextInputLayout = binding.contrasena
        val  boton: Button = binding.buttonSecond



        val logIn = binding.buttonSecond
//        val loading = binding.loading
//        val vista = binding.Lay
//        loading.visibility = View.INVISIBLE
//        vista.visibility = View.VISIBLE
        val usuario = binding.usuario
        val contrasena = binding.contrasena
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




        logIn.setOnClickListener {

            wifi( )
            clickWait()
            (activity as MainActivity?)?.PopUpLoading(true)

            try {

               val user = usertexto.text

                if (user!!.isNotEmpty() && passtexto.text!!.isNotEmpty()) {
                    //loading.displayLoading()
                    val myPost = Post(usertexto.text.toString(), passtexto.text.toString())

                    viewLogModel.changeState(loginViewModelState)
                    if (viewLogModel.uiState.value?.isUserLoggedIn == false) {
                        viewLogModel.pushPost(myPost)
                        viewLogModel.myResponse.observe(viewLifecycleOwner) { response ->

                            if (response.isSuccessful) {

                                Log.d("response", response.body()?.get(0).toString())

                                if (!response.body().isNullOrEmpty()) {

                                    val name = response.body()?.get(0)?.nombre_Completo.toString()
                                    val UserId = response.body()?.get(0)?.iD_Usuario
                                    val PostRegistro = PostRegistro(UserId!!, name, "Empleado")

                                    viewLogRegModel.pushPostRegistro(PostRegistro)

                                    viewLogRegModel.myResponse.observe(viewLifecycleOwner) { r ->

                                        val sharedPref = this.requireActivity().getSharedPreferences("UsD",Context.MODE_PRIVATE)
                                        val editor = sharedPref.edit()

                                        editor.putString("userName", name)
                                        editor.putString("userID", UserId.toString())
                                        editor.putString("id", r.body()?.id.toString())
                                        editor.putString("idKiosko", user.toString())
                                        editor.putBoolean("primer", true)
                                        editor.apply()

                                        findNavController().navigate(R.id.action_login_to_remember)

                                        usertexto.isClickable=false
                                        passtexto.isClickable=false
                                        usuariooo.isClickable=false
                                        boton.isClickable=false
                                        usuariooo.isClickable=false
                                        pass.isClickable=false

                                        clickReady()
                                        (activity as MainActivity?)?.PopUpLoading(false)
                                    }

                                    //viewLogModel.myResponse.removeObservers(viewLifecycleOwner)
                                } else {

                                    clickReady()
                                    (activity as MainActivity?)?.PopUpLoading(false)

                                    showMaterialDialog(
                                        "Error",
                                        "Usuario y/o contraseña incorrectos."
                                    )
                                }
                                //viewLogModel.myResponse.removeObservers(viewLifecycleOwner)
                            } else {
                                clickReady()
                                (activity as MainActivity?)?.PopUpLoading(false)

                                showMaterialDialog("Error",
                                    "Usuario y/o contraseña incorrectos.")

                            }
                            //viewLogModel.myResponse.removeObservers(viewLifecycleOwner)
                        }

                    } else {

                        clickReady()
                        "Usuario y/o contraseña incorrectos."
                        (activity as MainActivity?)?.PopUpLoading(false)
                        showMaterialDialog("Error",
                            "Usuario y/o contraseña incorrectos.")


                    }
                }
                //El usuario y contraseña son requeridos mensaje
                else {
                    clickReady()
                    //loading.hideLoading()
                    (activity as MainActivity?)?.PopUpLoading(false)

                    showMaterialDialog("Error", "El usuario y contraseña son requeridos.")
                }
            }catch(e:Exception){
                Log.d("exeption","exeption")
            }finally{
               Log.d("final","final")
//                loadingOFF(loading,vista)
              //  (activity as MainActivity?)?.PopUpLoading(false)

            }

        }

    }

    fun clickWait() {
        val  usertexto: TextView = binding.usuariotxt
        val  passtexto: TextView = binding.contrasenatxt
        val  usuariooo: TextInputLayout = binding.usuario
        val  pass: TextInputLayout = binding.contrasena
        val  boton: Button = binding.buttonSecond

        usertexto.isEnabled=false
        passtexto.isEnabled=false
        usuariooo.isEnabled=false
        boton.isEnabled=false
        usuariooo.isEnabled=false
        pass.isEnabled=false
    }
    fun clickReady() {

        val  usertexto: TextView = binding.usuariotxt
        val  passtexto: TextView = binding.contrasenatxt
        val  usuariooo: TextInputLayout = binding.usuario
        val  pass: TextInputLayout = binding.contrasena
        val  boton: Button = binding.buttonSecond

        usertexto.isEnabled=true
        passtexto.isEnabled=true
        usuariooo.isEnabled=true
        boton.isEnabled=true
        usuariooo.isEnabled=true
        pass.isEnabled=true

    }

     fun wifi() {
        val continuar: Button = requireView().findViewById<View>(R.id.buttonSecond) as Button


            if ((activity as MainActivity?)?.isNetDisponible() == true){
                if((activity as MainActivity?)?.isOnlineNet() == true){
                        continuar.isEnabled = true
                        (activity as MainActivity?)?.checkConnectivity()


                }
            }else{
                continuar.isEnabled = false
                (activity as MainActivity?)?.checkConnectivity()

            }

    }




}