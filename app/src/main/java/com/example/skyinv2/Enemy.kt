package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Enemy (res : Resources){

    var enemy : Bitmap
    val width : Int
    private val height : Int
    var x : Int
    var y : Int
    var speedIncrease : Int
    var speed : Int

    init {
        enemy = BitmapFactory.decodeResource(res, R.drawable.enemy)
        x = 2000
        y= 100
        speed = (15..30).random()
        height = enemy.height
        width = enemy.width
        enemy = Bitmap.createScaledBitmap(enemy, width, height, false)
        speedIncrease = 0


    }


    fun spawn(screenHeight: Int, playerY : Int, enemies : MutableList<Enemy>){


        // cette variable newY est utilisée pour stocker la position y de l'ennemi
        var newY: Int
        // cette boucle do while permet de vérifier si la position y de l'ennemi est déjà occupée par un autre ennemi
        do {
            // on génère une nouvelle position y pour l'ennemi
            newY = (0..screenHeight-150).random()
            // on vérifie si la position y de l'ennemi est déjà occupée par un autre ennemi
        } while (enemies.any { enemy -> Math.abs(enemy.y - newY) < height })


        when (playerY) {
            750 -> {
                x=2000
                y= 750 + 150
                speedIncrease+= 0.5.toInt()
                speed += speedIncrease
            }
            -150 -> {
                x=2000
                y= 0
                speedIncrease+= 0.5.toInt()
                speed += speedIncrease
            }
            else -> {x = 2000
                y = (0..screenHeight-150).random()
                speedIncrease+= 0.5.toInt()
                speed += speedIncrease}
        }



    }
}