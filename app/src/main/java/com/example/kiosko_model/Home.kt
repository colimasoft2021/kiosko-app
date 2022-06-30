package com.example.kiosko_model

import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
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
import com.google.android.material.progressindicator.CircularProgressIndicator


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
//        val a = R.id.nav_host_fragment_content_home
//        Log.d("idvalorInt", a.toString())
//        val navController = findNavController(R.id.nav_host_fragment_content_home)

        // menu de navegacion lateral



        val navBar = binding.navigationBotommm
        //control de navegación ligado a los fragments
//        navBar.setupWithNavController(navController)

        navBar.setOnItemSelectedListener () {
            when(it.itemId){
                R.id.inicio -> {
                    val intent = Intent(this, Home::class.java)

                    if (isOnlineNet() == true){ startActivity(intent) }
                    else {
                        checkConnectivity()
                    }
                    true

                }

                R.id.accesoDirecto -> {

                    if (isOnlineNet() == true){
                        findNavController(R.id.nav_host_fragment_content_home).navigate(R.id.accesoDirectoFragment)
                    }
                    else {
                        checkConnectivity()
                    }

                        true
                }

                else -> {
                    true
                }
            }
        }


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

    override fun onResume() {
        super.onResume()
        val btnPerfil = findViewById<ImageButton>(R.id.perfilFoto)
        btnPerfil?.setOnClickListener {
            val intent = Intent(this, Perfil::class.java)
            startActivity(intent)
        }

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

//    fun  setNotifications(numer : Int) {
//
//        val navBar = binding.navigationBotommm
//
//        val badge = navBar.getOrCreateBadge(R.id.notificacionesFragment)
//        if (numer != 0){
//            badge.isVisible = true
//            badge.number = numer
//        } else {
//            badge.isVisible = false
//        }
//        val toast = Toast.makeText(this, numer.toString(), Toast.LENGTH_SHORT)
//        toast.show()
//
//    }

    fun PopUpLoading(loading:Boolean) {

        val load = findViewById<CircularProgressIndicator>(R.id.load)
        if (!loading) {
            load.visibility = View.GONE
            load.isClickable = true


        } else {
            load.visibility = View.VISIBLE
            load.isClickable = false

        }
    }



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


    fun isNetDisponible(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val actNetInfo = connectivityManager.activeNetworkInfo
        return actNetInfo != null && actNetInfo.isConnected
    }

    fun isOnlineNet(): Boolean? {
        try {
            val p = Runtime.getRuntime().exec("ping -c 1 www.google.es")
            val `val` = p.waitFor()
            return `val` == 0
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return false
    }

    fun checkConnectivity() {
        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo

        if (null == activeNetwork) {
            val dialogBuilder = AlertDialog.Builder(this)
            val intent = Intent(this, MainActivity::class.java)
            // set message of alert dialog
            dialogBuilder.setMessage("Confirme su conexión a internet, e intente de nuevo")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Salir", DialogInterface.OnClickListener { dialog, id ->

//                    recreate()
//                    finish()
                })
            // negative button text and action
//                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
//                    finish()
//                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Wi-FI desactivado ")
            alert.setIcon(R.mipmap.ic_launcher)
            // show alert dialog
            alert.show()
        }
    }



}