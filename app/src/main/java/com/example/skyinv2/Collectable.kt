package com.example.skyinv2

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Collectable (res : Resources,screenX: Int, screenY: Int): MovementObject {
    var x : Int
    var y : Int
    private val ScreenY : Int
    private val ScreenX : Int
    private var speed : Int
    private lateinit var speedthread : Thread
    private var Speedeffect : Boolean = false
    private var wich : Int
    private val resource : Resources
    var etat : Boolean = false
    //speedcols
    var speedcol : Bitmap
    var widthsp : Int
    var heightsp : Int
    var speedcols : MutableList<Collectable>
    //coins
    var coin : Bitmap
    var widthcoin : Int
    var heightcoin : Int
    var coins : MutableList<Collectable>
    //coin multiplicator
    var coinmult : Bitmap
    var widthcoinmult : Int
    var heightcoinmult : Int
    var coinmults : MutableList<Collectable>
    //missile bonus
    var missileadd : Bitmap
    var widthmissileadd : Int
    var heightmissileadd : Int
    var missileadds : MutableList<Collectable>
    //ennemies




    init {
        x = 2000
        y = 100
        speed = 15

        speedcol = BitmapFactory.decodeResource(res, R.drawable.speedcolok)
        widthsp = speedcol.width
        widthsp /= 13
        heightsp = speedcol.height
        heightsp /= 13
        speedcol = Bitmap.createScaledBitmap(speedcol, widthsp, heightsp, false)

        coin = BitmapFactory.decodeResource(res, R.drawable.coin)
        widthcoin = coin.width
        widthcoin /= 12
        heightcoin = coin.height
        heightcoin /= 12
        coin = Bitmap.createScaledBitmap(coin, widthcoin, heightcoin, false)


        coinmult =  BitmapFactory.decodeResource(res, R.drawable.coinmultiplicator)
        widthcoinmult = coinmult.width
        widthcoinmult /= 11
        heightcoinmult = coinmult.height
        heightcoinmult /= 11
        coinmult = Bitmap.createScaledBitmap(coinmult, widthcoinmult, heightcoinmult, false)

        missileadd =  BitmapFactory.decodeResource(res, R.drawable.bonusmissile)
        widthmissileadd = missileadd.width
        widthmissileadd /= 13
        heightmissileadd = missileadd.height
        heightmissileadd /= 13
        missileadd= Bitmap.createScaledBitmap(missileadd, widthmissileadd, heightmissileadd, false)

        wich = 0
        coinmults = mutableListOf()
        speedcols= mutableListOf()
        coins = mutableListOf()
        missileadds = mutableListOf()

        ScreenY = screenY
        ScreenX = screenX
        resource = res

    }


    private fun spawn (screenY : Int, screenX: Int){
        x = screenX
        y = (0..screenY-75).random()
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


    override fun move() {
        x -= speed
    }




     fun collectableFactory() {
        val spawnDelay = (4000..10000).random()
         Thread {
            Thread.sleep(spawnDelay.toLong())
            wich =(1..4).random()
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
             if (wich==3){
                 val newmissileadd = Collectable(resource,ScreenX, ScreenY )
                 newmissileadd.spawn(ScreenY, ScreenX )
                 missileadds.add(newmissileadd)
             }
             if (wich==4){
                 val newmcoinmult = Collectable(resource,ScreenX, ScreenY )
                 newmcoinmult.spawn(ScreenY, ScreenX )
                 coinmults.add(newmcoinmult)
             }

            if(etat) { //etat ne s'update pas dcp il y a un bug, je pense qu'il faudrait faire genre isPlaying.etat avec etat etant obj de skyview
                collectableFactory() // Appel récursif pour continuer à faire apparaître des objets
            }
            }.start()

    }

}





