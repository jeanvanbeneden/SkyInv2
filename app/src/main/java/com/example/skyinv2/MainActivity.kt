package com.example.skyinv2


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button


class  MainActivity : AppCompatActivity() {
    private lateinit var play : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        play = findViewById(R.id.play)

        play.setOnClickListener {
            startActivity(Intent(this@MainActivity, SkyInvActivity::class.java))
        }
    }
}


