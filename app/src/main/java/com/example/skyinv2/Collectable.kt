package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Collectable (res : Resources) {
    var x : Int
    var y : Int
    var speedcol : Bitmap
    var speed : Int
    private var widthsp : Int
    private var heightsp : Int



    init {
        x = 2000
        y = 100
        speed = 15
        speedcol = BitmapFactory.decodeResource(res, R.drawable.speedcolok)
        widthsp = speedcol.width
        widthsp /= 12
        heightsp = speedcol.height
        heightsp /= 12
        speedcol = Bitmap.createScaledBitmap(speedcol, widthsp, heightsp, false)



    }


    fun spawn (screenY : Int){
        x = 2000
        y = (0..screenY-75).random()
        if (x<-2000){
            x=2000
        }
    }

    fun speedeffect(player: Player, enemies: MutableList<StraightEnemy>, enemy2 : FollowerEnemy, background1 : Background, background2: Background){
        if (player.x < x + widthsp &&
            player.x + player.width > x &&
            player.y < y + heightsp &&
            player.y + player.height > y){
            for(enemy in enemies){
                enemy.speed += 1
            }
            enemy2.speed += 1
        }
    }

}