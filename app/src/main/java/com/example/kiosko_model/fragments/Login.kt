package com.example.kiosko_model.fragments

import android.app.AppOpsManager
import android.content.Context
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
//        binding.lifecycleOwner = this
//        binding.viewModel = viewModel
        return binding.root

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
            val loadinDialog = LoadingScreen()
            loadinDialog.LoadingDialog(requireActivity())

           if (usertexto.text!!.isNotEmpty()&&passtexto.text!!.isNotEmpty()) {
//            findNavController().navigate(R.id.action_login_to_remember)

//               val intent = Intent(activity, Load::class.java)
//               startActivity(intent)

               loadinDialog.loadingAnimation()
               val myPost = Post(usertexto.text.toString(), passtexto.text.toString())

               viewLogModel.pushPost(myPost)
                            viewLogModel.myResponse.observe(viewLifecycleOwner,
                                Observer { response ->

                                    if (response.isSuccessful) {

                                            Toast.makeText(context,
                                                "Bienvenido ${
                                                    response.body()?.get(0)?.nombre_Completo
                                                }",
                                                Toast.LENGTH_SHORT).show()

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


                                            findNavController().navigate(R.id.action_login_to_remember)
                                            loadinDialog.dismisDialog()

                                        })


                                        usertexto.text = ""
                                        passtexto.text = ""
                                    } else {

                                        usertexto.text = ""
                                        passtexto.text = ""

                                        loadinDialog.dismisDialog()
                                        Toast.makeText(context,"Datos incorrectos",
                                            Toast.LENGTH_SHORT).show()
                                        viewLogModel.error.observe(viewLifecycleOwner, Observer { response ->


                                        })

                                    }


                                })

            }else{

               Toast.makeText(context,
                   "Falta algun Dato",
                   Toast.LENGTH_SHORT).show()
            }

        }
    }



}