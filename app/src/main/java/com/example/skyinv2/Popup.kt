package com.example.skyinv2

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button

class PausePopup(private val context: Context, private val screenWidth: Int, private val screenHeight: Int) :
    Dialog(context) {

    init {
        setContentView(R.layout.pause_popup_layout)
        val width = (screenWidth * 0.8).toInt()
        val height = (screenHeight * 0.6).toInt()
        window?.setLayout(width, height)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val resumeButton = findViewById<Button>(R.id.resume_button)
        val menuButton = findViewById<Button>(R.id.menu_button)


        resumeButton.setOnClickListener {

            dismiss()
        }

        menuButton.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            dismiss() // Ferme le Popup après avoir appuyé sur "Menu"
        }
    }





}