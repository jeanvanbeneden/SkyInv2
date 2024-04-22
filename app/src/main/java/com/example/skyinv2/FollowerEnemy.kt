package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class FollowerEnemy(res: Resources) : Enemyy() {
    var enemyfollower: Bitmap
    private var width: Int
    private var height: Int


    init {
        enemyfollower = BitmapFactory.decodeResource(res, R.drawable.followerenemy2)
        height = enemyfollower.height
        height /= 4
        width = enemyfollower.width
        width /= 4
        enemyfollower= Bitmap.createScaledBitmap(enemyfollower, width, height, false)
    }
    override fun spawn(screenHeight: Int, playerY: Int) {

        when (playerY) {
            750 -> {
                x = 2000
                y = 800
            }
            -150 -> {
                x = 2000
                y = -110
            }
        }
        speedIncrease += 0.5.toInt()
        speed += speedIncrease

    }
}