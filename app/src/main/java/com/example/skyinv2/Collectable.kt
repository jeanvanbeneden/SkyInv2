package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.random.Random

class Collectable (res : Resources): GameObject{
    var x : Int
    var y : Int
    var speedcol : Bitmap
    private var speed : Int
    private var widthsp : Int
    private var heightsp : Int
    private lateinit var speedthread : Thread
    private var Speedeffect : Boolean = false
    private var wich : Int
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
        wich = 0


    }


    fun spawn (screenY : Int){
        x = 2000
        y = (0..screenY-75).random()
        if (x<-2000){
            x=2000
        }
    }

    fun speedeffect(player: Player, enemies: MutableList<StraightEnemy>, enemy2 : FollowerEnemy, background1 : Background, background2: Background){

        if (player.x < x + widthsp  &&
            player.x + player.width > x  &&
            player.y < y + heightsp  &&
            player.y + player.height > y ){
                Speedeffect = true
                speedthread = Thread {
                while (Speedeffect) {
                    for (enemy in enemies) {
                        enemy.speed += 1
                    }
                    enemy2.speed += 1
                    background1.speed = 15
                    background2.speed = 15
                    Thread.sleep(10000) // Une pause de 10 secondes         //ici le spped s'arrete après 10 sec mais jsp cmt faire pour que le speed s'applique sur les nouveaux hélicos
                    Speedeffect = false
                    background1.speed = 10
                    background2.speed = 10
                }
            }
            speedthread.start()

            }


    }

    override fun interactions() {
        TODO("Not yet implemented")
    }

    override fun move() {
        x -= speed
    }



    private fun spawnObject() {
        val spawnDelay = (5000..12000).random()
        Thread {
            Thread.sleep(spawnDelay.toLong())
            wich =(1..2).random()



            spawnObject() // Appel récursif pour continuer à faire apparaître des objets
            }.start()

    }

}

