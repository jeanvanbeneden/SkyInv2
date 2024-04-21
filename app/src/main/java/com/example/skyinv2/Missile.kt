package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Missile ( playerx : Int ,playery : Int, nombre : Int,ScreenY : Int, ScreenX : Int, res : Resources) {
    var missile: Bitmap
    var y: Int
    var x: Int
    private var width: Int
    private var height: Int
    var active : Boolean = false

    init {
        missile = BitmapFactory.decodeResource(res, R.drawable.missile)
        width = missile.width
        height = missile.height
        width /= 25
        height /= 25
        y = playery
        x = playerx
        missile = Bitmap.createScaledBitmap(missile, width, height, false)

    }

    fun Moveforward() {
        x += 15
    }
}