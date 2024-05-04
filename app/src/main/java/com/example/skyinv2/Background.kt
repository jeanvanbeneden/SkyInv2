package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Background (screenX: Int, screenY: Int, res: Resources){
    var x : Int =0
    var y : Int =0
    var background : Bitmap
    var speed : Int


    init {
        background = BitmapFactory.decodeResource(res, R.drawable.backgroundwar)
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false)
        speed = 10
    }
}









