package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Missile(playerx: Int, playery: Int, ScreenY: Int, ScreenX: Int, res: Resources) : MovementObject , CheckCollision {
    var missile: Bitmap
    var y: Int
    var x: Int
    private var width: Int
    private var height: Int
    var active : Boolean = false
    private var ennemifol : FollowerEnemy

    init {
        missile = BitmapFactory.decodeResource(res, R.drawable.missile)
        width = missile.width
        height = missile.height
        width /= 25
        height /= 25
        y = playery
        x = playerx
        missile = Bitmap.createScaledBitmap(missile, width, height, false)
        ennemifol = FollowerEnemy(res)

    }

    override fun interactions(elem: Any): Boolean {
        if(elem is FollowerEnemy) {
            val widthef: Int = ennemifol.width
            val heightef : Int = ennemifol.height
            if (x < ennemifol.x + widthef &&
                x + width > ennemifol.x &&
                y < ennemifol.y + heightef &&
                y + height > ennemifol.y) {return true}
        }

        return false
    }
    override fun move(){
        moveforward()
    }
    private fun moveforward() {
        x += 25
    }

}