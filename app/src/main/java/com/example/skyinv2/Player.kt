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


    override fun interaction1(player: Player, coin: Collectable): Boolean {
        return player.x < coin.x + coin.widthcoin - 100 &&
                player.x + player.width > coin .x &&
                player.y < coin.y + coin.heightcoin - 150 &&
                player.y + player.height -150 > coin.y
    }

    override fun interaction2(player: Player, speedcoil: Collectable): Boolean {
        return player.x < speedcoil.x + speedcoil.widthsp -100 &&
                player.x + player.width > speedcoil.x &&
                player.y < speedcoil.y + speedcoil.heightsp -150 &&
                player.y + player.height -150 > speedcoil.y
    }

    override fun interaction3(player: Player, coinmult: Collectable): Boolean {
        return player.x < coinmult.x + coinmult.widthcoinmult -100 &&
                player.y < coinmult.y + coinmult.heightcoinmult -250 &&
                player.x + player.width > coinmult.x &&
                player.y + player.height -150 > coinmult.y
    }
    override fun interaction4(player: Player, missileadd: Collectable): Boolean {
        return player.x < missileadd.x + missileadd.widthmissileadd -100 &&
                player.y < missileadd.y + missileadd.heightmissileadd &&
                player.x + player.width > missileadd.x &&
                player.y + player.height -150 > missileadd.y
    }

    override fun move() {x
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
    companion object{
        private var INSTANCE: Player? = null

        fun getInstance(screenY: Int, screenX: Int, res : Resources): Player{
            if (INSTANCE == null){
                INSTANCE = Player(screenY, screenX, res)
            }
            return INSTANCE!!

        }
    }



}