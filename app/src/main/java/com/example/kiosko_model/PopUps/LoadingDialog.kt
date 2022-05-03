package com.example.kiosko_model.PopUps

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.content.Context
import android.util.Log
import com.example.kiosko_model.MainActivity
import com.example.kiosko_model.R
import com.example.kiosko_model.fragments.Login

/*class LoadingDialog(val mActivity: Activity) {
    lateinit var dialog: Dialog

    fun displayLoading() {
        dialog = Dialog(mActivity)
        dialog.setContentView(R.layout.activity_loading_spash_screen)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.create()
        dialog.show()
    }

    fun hideLoading() {
        Log.d("ocultar","dsdsds")
        dialog.dismiss()
    }
}*/
class LoadingDialog(val mActivity: Activity) {
    private lateinit var dialog: AlertDialog

    fun displayLoading() {
        var inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_dialog, null)
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun hideLoading() {
        Log.d("ocultar","dsdsds")
        dialog.dismiss()
    }
}