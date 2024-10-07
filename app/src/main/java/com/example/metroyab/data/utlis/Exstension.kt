package com.example.metroyab.data.utlis

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity

fun Context.dialog(layout: Int, view: View, cancelable: Boolean): Dialog {
    val dialog = Dialog(this)
    dialog.setContentView(layout)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window!!.setLayout(view.width, view.height)
    dialog.setCancelable(cancelable)
    dialog.show()
    return dialog
}

fun FragmentActivity.onBackPressed(callback: () -> Unit) {
    onBackPressedDispatcher.addCallback(this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                callback()
            }
        }
    )
}

