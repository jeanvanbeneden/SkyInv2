package com.example.skyinv2

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

class PausePopup(
    private val context: Context,
    private val screenWidth: Int,
    private val screenHeight: Int
) : Dialog(context) {

    init {
        setContentView(R.layout.pause_popup_layout)
        // Configuration du Popup ici
        val width = (screenWidth * 0.8).toInt()
        val height = (screenHeight * 0.6).toInt()
        window?.setLayout(width, height)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}