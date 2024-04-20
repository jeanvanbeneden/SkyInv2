package com.example.skyinv2


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager


    class SkyInvActivity : AppCompatActivity() {

        private lateinit var skyView: SkyView


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)


            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)


            val screenHeight = resources.displayMetrics.heightPixels
            val screenWidth = resources.displayMetrics.widthPixels
            skyView = SkyView(this, screenWidth, screenHeight)

            //ici on affiche la vue sur l'écran
            setContentView(skyView)
        }

        override fun onResume() {
            super.onResume()
            skyView.resume()
        }

        override fun onPause() {
            super.onPause()
            skyView.pause()
        }
    }





