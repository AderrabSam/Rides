package com.assignment.rides

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable


class LoadingDialog(private val context: Context) {

    private lateinit var dialog: Dialog

    fun startLoadingDialog() {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.create()
        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }

}