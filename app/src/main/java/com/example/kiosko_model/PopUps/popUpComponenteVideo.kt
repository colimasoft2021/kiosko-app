package com.example.kiosko_model.PopUps

import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kiosko_model.R
import com.example.kiosko_model.databinding.ActivityPopUpComponenteVideoBinding
import com.google.android.material.progressindicator.CircularProgressIndicator


class popUpComponenteVideo : AppCompatActivity() {
        private  var _binding: ActivityPopUpComponenteVideoBinding? = null
        private  val binding get() = _binding!!

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            _binding = ActivityPopUpComponenteVideoBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val displaymetrics = DisplayMetrics()
            this.windowManager?.defaultDisplay?.getMetrics(displaymetrics)
            val height = displaymetrics.heightPixels
            val width = displaymetrics.widthPixels

            val texto = intent.extras!!.getString("texto")
            val url2 = intent.extras!!.getString("url")
            val mensajeIncial = intent.extras!!.getBoolean("MensajeInicial")
//
//        val sharedPref = this
//            .getSharedPreferences("AvisoInicial", Context.MODE_PRIVATE)
//
//        val title = sharedPref.getString("title","")
//        val url = sharedPref.getString("url","")
//        val size = sharedPref.getString("size","")
            binding.titlepopV.text = texto

            val video = binding.messageV

            val uri = Uri.parse(url2)
            val contenedor = binding.contentPopVideo

            val mediaController = MediaController(this)
            video.setVideoURI(uri)
            video.setMediaController(mediaController)
            mediaController.setAnchorView(contenedor)
            mediaController.setPadding(0,0,width/10+width/20,height/3)

            video.requestFocus()
            // starting the video

            video.setOnPreparedListener {

                video.start()
                PopUpLoading(false)
            }


                    // display a toast message if any
                    // error occurs while playing the video
/*            video.setOnErrorListener { mp, what, extra ->
                        Toast.makeText(applicationContext, "An Error Occured " +
                                "While Playing Video !!!", Toast.LENGTH_LONG).show()
                        false
            }*/

/*            if (isNetDisponible()){
                when (isOnlineNet()){
                    true -> {
                        checkConnectivity()

                    }
                    false -> {
                        checkConnectivity()
                    }

                    else -> {
                        checkConnectivity()
                    }

                }
            }else{
                checkConnectivity()

            }*/

            mediaController.setAnchorView(video)
            hideSystemUI()
//
            if (height > 1000 && width>600 ){

                if (height > 1400  && width>1000 ){
                    val width2 = 1000
                    val height2 = 1300
                    window.setLayout(width2, height2)


                }else{
                    val width2 = 700
                    val height2 = 1100
                    window.setLayout(width2, height2)
                }

            }
            else{
                val width2 = 550
                val height2 = 650
                window.setLayout(width2, height2)
            }

        val params =  WindowManager.LayoutParams()
            params.gravity= Gravity.CENTER
            params.x = 0
            params.y = - 20
//

            if(mensajeIncial==true){
                video.setOnCompletionListener { //Termina reproduccion,
                    //Realiza Intent.
                    binding.closeV.setOnClickListener {
                        finish()
                    }
                }
            }else{
                binding.closeV.setOnClickListener {
                    finish()
                }
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


        fun PopUpLoading(loading:Boolean) {

            val load = findViewById<CircularProgressIndicator>(R.id.loadComponenteVideo)
            if (!loading) {
                load.visibility = View.GONE
                load.isClickable = true


            } else {
                load.visibility = View.VISIBLE
                load.isClickable = false

            }
        }


    }
/*class MyService : Service(), MediaPlayer.OnErrorListener {

    private var mediaPlayer: MediaPlayer? = null

    fun initMediaPlayer() {
        // ...initialize the MediaPlayer here...
        mediaPlayer?.setOnErrorListener(this)
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        // ... react appropriately ...
        // The MediaPlayer has moved to the Error state, must be reset!
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}*/

