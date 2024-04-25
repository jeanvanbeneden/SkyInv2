package com.example.skyinv2


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView


class SkyInvActivity : AppCompatActivity() {

        private lateinit var skyView: SkyView
        private lateinit var collectable: Collectable

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)


            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)


            val screenHeight = resources.displayMetrics.heightPixels
            val screenWidth = resources.displayMetrics.widthPixels
            collectable = Collectable(resources, screenHeight)
            skyView = SkyView(this, screenWidth, screenHeight, collectable)





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





