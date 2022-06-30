package com.example.kiosko_model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import com.example.kiosko_model.databinding.ActivityRememberBinding
import com.example.kiosko_model.models.*
import com.example.kiosko_model.repository.Repository

class Remember : AppCompatActivity() {

    private lateinit var viewModel: NotificacionesViewModel
    private lateinit var binding: ActivityRememberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()

        binding = ActivityRememberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        hideSystemUI()
        val sharedPref = getSharedPreferences("UsD", Context.MODE_PRIVATE)

        val name = sharedPref.getString("userName","defaultName") ?: "NOMBRE"
        val id = Id(sharedPref.getString("idKiosko","defaultName")!!.toInt())

        val continuar = binding.start

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



        continuar.setOnClickListener {

            if (isNetDisponible()){
                when (isOnlineNet()){
                    true -> {
//                        continuar.isEnabled = true
                        checkConnectivity()

                        val intent = Intent(this, Home::class.java)
                        startActivity(intent)
                    }
                    false -> {
//                        continuar.isEnabled = false
                        checkConnectivity()
                    }

                    else -> {
//                        continuar.isEnabled = false
                        checkConnectivity()
                    }

                }
            }else{
                checkConnectivity()
//                continuar.isEnabled = false

            }

        }

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
            // set message of alert dialog
            dialogBuilder.setMessage("Confirme su conexión a internet, e intente de nuevo")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Salir", DialogInterface.OnClickListener { dialog, id ->
                    recreate()
//                    finish()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Wi-FI desactivado ")
            alert.setIcon(R.mipmap.ic_launcher)
            // show alert dialog
            alert.show()
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

        val channelId = "ChannelKioskoApp"
        val notificationId = id

        val intent = Intent(this, Home::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(titulo)
                .setContentText(modulo)
                .setStyle(NotificationCompat.BigTextStyle()
                .bigText(contenido))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }


    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val CHANNEL_ID = "ChannelKioskoApp"
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}