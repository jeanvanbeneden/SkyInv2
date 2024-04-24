package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Background (screenX: Int, screenY: Int, res: Resources){
    var x : Int
    var y : Int
    var background : Bitmap
    var speed : Int


    init {
        x = 0
        y = 0
        background = BitmapFactory.decodeResource(res, R.drawable.backgroundwar)
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false)
        speed = 10
    }
}









