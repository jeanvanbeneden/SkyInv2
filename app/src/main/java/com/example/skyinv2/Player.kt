package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Player (screenY : Int, screenX: Int, res : Resources){
    val x : Int
    var y : Int
    var player : Bitmap
    private var width : Int
    var height : Int
    var up : Boolean
    var down : Boolean

    init {
        player = BitmapFactory.decodeResource(res, R.drawable.helico)
        width = player.width
        height = player.height
        width /=3
        height /=3
        y = screenY / 2 -height/2
        x = screenX / 2 -width
        player = Bitmap.createScaledBitmap(player, width, height, false)
        up = false
        down = true
    }


    fun moveUp(){
        y -= 5
    }

    fun moveDown(){
        y += 5
    }



}