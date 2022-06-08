package com.example.kiosko_model

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.kiosko_model.PopUps.Loading
import com.example.kiosko_model.databinding.ActivityMainBinding
import com.google.android.material.progressindicator.CircularProgressIndicator


class   MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        hideSystemUI()

//                WindowCompat.setDecorFitsSystemWindows(false)



        val sharedPref = getSharedPreferences("UsD", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("userName", "" )
        editor.putString("userID", "" )
        editor.apply()


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
            val intent = Intent(this, MainActivity::class.java)
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

    fun a(){
        // Declaring Wi-Fi manager
        val wifi = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager

        // On button Click

            // wifi.isWifiEnabled is a boolean, is Wi-Fi is ON,
            // it switches down and vice-versa
            wifi.isWifiEnabled = true
    //            = !wifi.isWifiEnabled
//
//            // For displaying Wi-fi status in TextView
//            if (!wifi.isWifiEnabled) {
//                //wifi
//            } else {
////                no wifi
//            }
    }
    fun PopUpLoading(loading:Boolean){

        val load  = findViewById<CircularProgressIndicator>(R.id.load)
        if(!loading){
            load.visibility = View.GONE
            load.isClickable = true


        }
        else{
            load.visibility = View.VISIBLE
            load.isClickable = false

        }



    }

}