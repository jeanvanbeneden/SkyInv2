package com.example.skyinv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.content.Context

class MainActivity : AppCompatActivity() {
    private lateinit var play: Button
    private lateinit var highScoreTextView: TextView 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        play = findViewById(R.id.play)
        highScoreTextView = findViewById(R.id.recordTxt) // Ajoutez cette ligne

        play.setOnClickListener {
            startActivity(Intent(this@MainActivity, SkyInvActivity::class.java))
        }

    }
    override fun onResume() {
        super.onResume()
        val sharedPrefs = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
        val highScore = sharedPrefs.getInt("HighScore", 0)
        highScoreTextView.text = "Record: $highScore"
    }
}