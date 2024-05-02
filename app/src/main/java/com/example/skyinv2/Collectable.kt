package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Collectable (res : Resources,screenX: Int, screenY: Int): MouvementObject {
    var x : Int
    var y : Int
    val ScreenY : Int
    val ScreenX : Int
    var speedcol : Bitmap
    var coin : Bitmap
    private var speed : Int
    var widthsp : Int
    var heightsp : Int
    var widthcoin : Int
    var heightcoin : Int
    private lateinit var speedthread : Thread
    private var Speedeffect : Boolean = false
    private var wich : Int
    var speedcols : MutableList<Collectable>
    var coins : MutableList<Collectable>
    private val resource : Resources
    private var Coineffect : Boolean = false
    var etat : Boolean = false
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
        ScreenX = screenX
        resource = res

    }


    fun spawn (screenY : Int, screenX: Int){
        x = screenX
        y = (0..screenY-75).random()
        //if (x<-2000){
            //x=2000
        //}
    }

    fun speedeffect(enemies: MutableList<StraightEnemy>, enemy2 : FollowerEnemy, background1 : Background, background2: Background){
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
                    enemy2.speed -=1
                }
            }
            speedthread.start()
    }

    fun coineffect(){

    }

    override fun move() {
        x -= speed
    }



     fun collectableFactory() {
        val spawnDelay = (4000..10000).random()
         Thread {
            Thread.sleep(spawnDelay.toLong())
            wich =(1..2).random()
            if (wich==1){
                val newspeedcol = Collectable(resource,ScreenX, ScreenY )
                newspeedcol.spawn(ScreenY, ScreenX)
                speedcols.add(newspeedcol)
            }
            if (wich==2){
                val newcoin = Collectable(resource,ScreenX, ScreenY )
                newcoin.spawn(ScreenY, ScreenX )
                coins.add(newcoin)
            }

            if(etat) { //etat ne s'update pas dcp il y a un bug, je pense qu'il faudrait faire genre isPlaying.etat avec etat etant obj de skyview
                collectableFactory() // Appel récursif pour continuer à faire apparaître des objets
            }
            }.start()

    }

}

