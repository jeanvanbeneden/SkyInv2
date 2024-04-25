package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Player (screenY : Int, screenX: Int, res : Resources) : GameObject{
    val x : Int
    var y : Int
    var player : Bitmap
    var width : Int
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
        x = screenX / 2 - 1000
        player = Bitmap.createScaledBitmap(player, width, height, false)
        up = false
        down = true
    }

    override fun interactions() {
        TODO("Not yet implemented")
    }
    override fun move() {
        if (up){
            moveUp()
        }
        if(down){
            moveDown()
        }
    }

    fun moveUp(){
        y -= 7
    }

    fun moveDown(){
        y += 7
    }



}