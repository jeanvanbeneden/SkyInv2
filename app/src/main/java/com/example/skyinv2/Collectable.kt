package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.random.Random

class Collectable (res : Resources, screenY: Int): GameObject{
    var x : Int
    var y : Int
    val ScreenY : Int
    var speedcol : Bitmap
    var coin : Bitmap
    private var speed : Int
    private var widthsp : Int
    private var heightsp : Int
    private var widthcoin : Int
    private var heightcoin : Int
    private lateinit var speedthread : Thread
    private var Speedeffect : Boolean = false
    private var wich : Int
    var speedcols : MutableList<Collectable>
    var coins : MutableList<Collectable>
    private val resource : Resources


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
        coin = BitmapFactory.decodeResource(res, R.drawable.coin)
        widthcoin = coin.width
        widthcoin /= 12
        heightcoin = coin.height
        heightcoin /= 12
        coin = Bitmap.createScaledBitmap(coin, widthcoin, heightcoin, false)


        wich = 0
        speedcols= mutableListOf()
        coins = mutableListOf()
        ScreenY = screenY
        resource = res
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



     fun spawncollectable() {
        val spawnDelay = (4000..12000).random()
        Thread {
            Thread.sleep(spawnDelay.toLong())
            wich =(1..2).random()
            if (wich==1){
                val newspeedcol = Collectable(resource, ScreenY )
                newspeedcol.spawn(ScreenY)
                speedcols.add(newspeedcol)
            }
            if (wich==2){
                val newcoin = Collectable(resource, ScreenY )
                newcoin.spawn(ScreenY)
                coins.add(newcoin)
            }


            spawncollectable() // Appel récursif pour continuer à faire apparaître des objets
            }.start()

    }

}

