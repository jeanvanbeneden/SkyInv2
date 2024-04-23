package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class StraightEnemy(res: Resources) : Enemyy(), GameObject{

    var straightEnemy: Bitmap
    val width: Int
    val height: Int


    init {
        straightEnemy = BitmapFactory.decodeResource(res, R.drawable.enemy)
        height = straightEnemy.height
        width = straightEnemy.width
        straightEnemy = Bitmap.createScaledBitmap(straightEnemy, width, height, false)
    }

    override fun interactions() {
        TODO("Not yet implemented")
    }

    override fun move() {
        x -= speed
    }
}