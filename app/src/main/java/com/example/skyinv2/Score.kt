package com.example.skyinv2

class Score () {
    var score : Int
    private lateinit var scorethread : Thread
    var isPlaying : Boolean = true
    private lateinit var scoremultiplicator :Thread
    private var multiplicator : Boolean = false

    init {
        score =0
    }





    fun scorecount(){
        scorethread = Thread {
            while (isPlaying) {
                Thread.sleep(500)
                score++
            }
        }
        scorethread.start()
    }


    fun scorebonus(bonus : Int){
        score += bonus
    }

    fun doublescore(){
        multiplicator = true
        scoremultiplicator = Thread{
            Thread.sleep(20000)
            multiplicator = false

        }
        while(multiplicator){
            score++
        }
    }
}