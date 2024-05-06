package com.example.skyinv2

import kotlin.math.abs


open class Enemyy {


    var x: Int
    var y: Int
    var speedIncrease: Int
    var speed: Int

    init {
        x = 2000
        y = 100
        speed = (17..28).random()
        speedIncrease = 0

    }


    // cette fonction spawn permet de générer un ennemi à une position aléatoire sur l'écran
    open fun spawn(screenHeight: Int, playerY: Int) {
        x = 2200
        y = (0..screenHeight - 150).random()
        speedIncrease += 0.5.toInt()
        speed += speedIncrease

    }

    open fun spawn2(screenHeight: Int, playerY: Int, enemies: MutableList<StraightEnemy>, height : Int) {

        var newY: Int

        do { newY = (0..screenHeight - 150).random()
        } while (enemies.any { enemy -> abs(enemy.y - newY) < height })

    }
}