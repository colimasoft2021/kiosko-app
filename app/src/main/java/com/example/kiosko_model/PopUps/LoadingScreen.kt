package com.example.kiosko_model.PopUps

import android.app.Activity
import android.app.AlertDialog
import com.example.kiosko_model.R

class LoadingScreen {

        lateinit var  activity : Activity
        lateinit var dialog : AlertDialog

        fun LoadingDialog(myActivity: Activity){
            activity = myActivity
        }

        fun loadingAnimation() {
            val builder = AlertDialog.Builder(activity)
            val inflater = activity.layoutInflater

            builder.setView(inflater.inflate(R.layout.activity_loading_spash_screen,null))
            builder.setCancelable(false)
            dialog = builder.create()
            dialog.show()
        }

        fun dismisDialog(){
            dialog.dismiss()
        }

}