package com.example.kiosko_model

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kiosko_model.PopUps.Popup1
import com.example.kiosko_model.PopUps.Loading
import com.example.kiosko_model.PopUps.popUpComponente
import com.example.kiosko_model.PopUps.popUpComponenteVideo
import com.example.kiosko_model.databinding.ActivityHomeBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository


class Home : AppCompatActivity() {

    private  var _binding: ActivityHomeBinding? = null
    private  val binding get() = _binding!!

    private lateinit var toggle : ActionBarDrawerToggle
    // canal unico para las notificaciones
    private val channelId = "chanel"
    //identificador unico de las notificaciones
    private val notificationId = 12345
    // menu de navegacion bottom
    private lateinit var viewModel: ComponentesViewModel
    private val viewModel2: CompuestosViewModel by viewModels()
    private lateinit var avisoViewModel: MensajeInicialViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUI()

        val main = binding.main
        val repository = Repository()

        val viewModelFactoryAviso = MensajeInicialViewModelFactory(repository)

        avisoViewModel =  ViewModelProvider(this, viewModelFactoryAviso)[MensajeInicialViewModel::class.java]
        avisoViewModel.getAvisoInicial()

//        try{
//            avisoViewModel.AvisoResponse.observe(this) { response ->
////            Log.d("response Avisos", response.body().toString())
//                val size = response.body()!!.size
//                var index = 0
//
//                response.body()?.forEach {
//                    index++
//
//                    PopUp(it.descripcion,it.url)
//
////                    intentPopUp()
//                }
//            }
//        }catch (e: Error) {
//
//        }

        // Pop Up

        //define el control de navegacion de los fragmentos a un val para ligarlo a su menu correspondiente
        val navController = findNavController(R.id.nav_host_fragment_content_home)

        // menu de navegacion lateral



        val navBar = binding.navigationBotommm
        //control de navegación ligado a los fragments
        navBar.setupWithNavController(navController)
        /*
        navBar.setOnItemSelectedListener () {
            when(it.itemId){
                R.id.inicioFragment -> {
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.accesoDirectoFragment -> {
                    findNavController(R.id.nav_host_fragment_content_home).navigate(R.id.accesoDirectoFragment)
                    Toast.makeText(this, "Accesos directos", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    true
                }
            }
        }*/


        val sharedPref = getSharedPreferences("UsD", Context.MODE_PRIVATE)
        val nEmpleado = sharedPref.getString("userName","defaultName")
        val cEmpleado = sharedPref.getString("userID","defaultName")
        val i = sharedPref.getString("id","defaultName")

        val id = Id(sharedPref.getString("id","defaultName")!!.toInt())


        val viewModelFactory = ComponentsViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory)[ComponentesViewModel::class.java]
        viewModel.getComponentes(id)

          try  {
                viewModel.datos.observe(this) { response ->
//                    if (response.isSuccessful) {
//                        response.body()?.forEach {
//                            val menu: Menu = navView.getMenu()
//
//                            when (it!!.padre) {
//                                null -> {
//                                    menu.add(it.id, it.id, it.orden, it.titulo)
//                                    Id.add(it.id)
//                                }
//                            }
//                            Log.d("ResponseOfComponentes", Id.toString())
//
//
//                        }
//                    } else {
//                        Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
//                    }
                }
          } catch (e: Error){
              Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
          }

    }

//
//    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
//        return when(item.itemId){
//            R.id.inicio -> {
//                Log.d("ResponseOfComponentes", Id.toString())
//            true
//            }
//
//            else -> {
//                true
//            }
//        }
//    }

    override fun onResume() {
        super.onResume()
        val btnPerfil = findViewById<ImageButton>(R.id.perfilFoto)
        btnPerfil?.setOnClickListener {
            val intent = Intent(this, Perfil::class.java)
            startActivity(intent)
        }

    }


    fun PopUpLoading(){
        startActivity(Intent(this, Loading::class.java))
    }
    fun PopUp(descripcion:String,url:String){
        val intent = Intent(this, Popup1::class.java)
        intent.putExtra("texto", descripcion)
        intent.putExtra("url", url)

        startActivity(intent)
    }
    fun PopUpComponente(descripcion:String?,url:String?){
        val intent = Intent(this, popUpComponente::class.java)
        intent.putExtra("texto", descripcion)
        intent.putExtra("url", url)

        startActivity(intent)
    }
    fun PopUpComponenteVideo(descripcion:String?,url:String?,mensajeInicial:Boolean){
            val intent = Intent(this, popUpComponenteVideo::class.java)
            intent.putExtra("texto", descripcion)
            intent.putExtra("url", url)
            intent.putExtra("MensajeInicial", mensajeInicial)
            startActivity(intent)

    }

    fun  setNotifications(numer : Int) {

        val navBar = binding.navigationBotommm

        val badge = navBar.getOrCreateBadge(R.id.notificacionesFragment)
        if (numer != 0){
            badge.isVisible = true
            badge.number = numer
        } else {
            badge.isVisible = false
        }
        val toast = Toast.makeText(this, numer.toString(), Toast.LENGTH_SHORT)
        toast.show()

    }
// asi sera el load con esta funcion
//    private fun showDialog() {
//        // custom dialog
//        val dialog = Dialog(this)
//        dialog.setContentView(R.layout.activity_popup1)
//
//        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.show()
//    }
//    showDialog()




    fun notifications(titulo: String, contenido: String ){
// pending intent para acceder directamente a el fragmento de notififcaciones
        val pendingIntent: PendingIntent = NavDeepLinkBuilder(this)
            .setComponentName(Home::class.java)
            .setGraph(R.navigation.nav_home)
            .setDestination(R.id.guiasFragment)
            .createPendingIntent()
// builder de la notificacion
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(titulo)
            .setContentText(contenido)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // define el intent al que accederea el usuario al clickear la notificacion
            .setContentIntent(pendingIntent)
            //
            .setAutoCancel(true)

        //este with maneja la creacion de las notificaciones crea la notificacion
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

    fun hideSystemUI() {

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

}