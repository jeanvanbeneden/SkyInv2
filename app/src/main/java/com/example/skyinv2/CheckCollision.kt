package com.example.skyinv2

interface CheckCollision {


    fun interaction1(player: Player, coin: Collectable): Boolean
    fun interaction2(player: Player, speedcoil : Collectable): Boolean
    fun interaction3(player: Player, coinmult : Collectable): Boolean
    fun interaction4(player: Player, missileadd : Collectable): Boolean

}
