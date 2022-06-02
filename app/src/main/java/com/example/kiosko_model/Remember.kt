package com.example.kiosko_model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkBuilder
import com.example.kiosko_model.databinding.ActivityRememberBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository

class Remember : AppCompatActivity() {

    private lateinit var viewModel: NotificacionesViewModel
    private lateinit var binding: ActivityRememberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRememberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        hideSystemUI()
        val sharedPref = getSharedPreferences("UsD", Context.MODE_PRIVATE)

        val name = sharedPref.getString("userName","defaultName") ?: "NOMBRE"
        val id = Id(sharedPref.getString("idKiosko","defaultName")!!.toInt())

        val repository = Repository()
        val viewModelFactory = NotificacionesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[NotificacionesViewModel::class.java]
        viewModel.pushPostNotificaciones(id)

        try {
            viewModel.myResponse.observe(this) { response ->

                if (response.isSuccessful) {
                    response.body()?.usuariosAlertas?.forEach {

                        val nombre = it?.nombre
                        var i  = 0

                        it?.modulosInactivos?.forEach { notif ->
                            i++
                            val modulo = notif.modulo
                            val porcentaje = notif.porcentaje
                            val tiempoInactivo = notif.tiempoInactividad

                            val titulo = "$nombre has iniciado con "

                            val texto = "Recuerda continuar con tu curso $modulo, tu progreso actual es de $porcentaje y" +
                                    " tienes $tiempoInactivo Dias sin ingresar, no te detengas"


                            notifications(titulo,texto, modulo, i)

                        }

                    }


                }

            }
        } catch (e:Exception){

        } finally {

        }

        binding.TitleRemember.text = "¡RECUERDA! $name"


        binding.start.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

    }

    fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
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
    fun notifications(titulo: String, contenido: String, modulo: String, id : Int ){
        val channelId = "chanel"
        val notificationId = id
        // Create an Intent for the activity you want to start
        val resultIntent = Intent(this, Home::class.java)
        // Create the TaskStackBuilder
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(resultIntent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }// pending intent para acceder directamente a el fragmento de notififcaciones
//        val pendingIntent: PendingIntent = NavDeepLinkBuilder(this)
//            .setComponentName(Home::class.java)
//            .setGraph(R.navigation.nav_home)
//            .setDestination(R.id.inicioFragment)
//            .createPendingIntent()

// builder de la notificacion
        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.notification)
            setContentTitle(titulo)
            setContentText(modulo)
//            .setLargeIcon(R.drawable.ic_launcher_kiosko)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(contenido))
            setPriority(NotificationCompat.PRIORITY_HIGH)
            // define el intent al que accederea el usuario al clickear la notificacion
            setContentIntent(resultPendingIntent)

//            .setAutoCancel(true)
        }
        //este with maneja la creacion de las notificaciones crea la notificacion
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

}