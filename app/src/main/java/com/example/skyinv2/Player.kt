package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Player (screenY : Int, screenX: Int, res : Resources) : MovementObject, CheckCollision{
    val x : Int
    var y : Int
    var player : Bitmap
    private var width : Int
    private var height : Int
    var up : Boolean
    var down : Boolean
    private var collect : Collectable
    private var enemstr : StraightEnemy
    private var enemfol : FollowerEnemy

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
        collect = Collectable(res, screenX, screenY)
        enemstr = StraightEnemy(res)
        enemfol = FollowerEnemy(res)

    }

    override fun interactions(elem: Any): Boolean {

        when (elem) {
            in collect.coins -> {
                val widthc = collect.widthcoin
                val heightc = collect.heightcoin
                if (x <= collect.x + widthc &&
                    x + width >= collect.x &&
                    y < collect.y + heightc &&
                    y + height > collect.y) {
                    return true

                }}
            in collect.speedcols -> {
                val widths = collect.widthsp
                val heights = collect.heightsp
                if (x < collect.x + widths &&
                    x + width > collect.x &&
                    y < collect.y + heights &&
                    y + height > collect.y) {
                    return true
                }
            }
            is StraightEnemy -> {
                val widthest = enemstr.width
                val heightest = enemstr.height
                if (x < enemstr.x + widthest &&
                    x + width > enemstr.x &&
                    y < enemstr.y + heightest &&
                    y + height > enemstr.y) {
                    return true
                }
            }
        }

        return false
    }





    override fun move() {
        if (up){
            moveUp()
        }
        if(down){
            moveDown()
        }
    }

    private fun moveUp(){
        y -= 8
    }

    private fun moveDown(){
        y += 7
    }



}