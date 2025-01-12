package com.example.week3.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import com.example.week3.R

class LoadingUtils(val activity: Activity) {

    lateinit var  alertDialog: AlertDialog

    fun show(){
        val dialogview = activity.layoutInflater.inflate(R.layout.loading,null)

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Please Wait")
        builder.setView(dialogview) // Set the custom layout
        builder.setCancelable(false) // Optional: Prevent dismissal by tapping outside

        alertDialog = builder.create()
    alertDialog.show()
    }
    fun dismiss() {
            alertDialog.dismiss()

    }

}