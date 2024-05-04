package com.example.skyinv2

class Score {
    var score : Int = 0
    private lateinit var scorethread : Thread
    var isPlaying : Boolean = true
    private lateinit var scoremultiplicator :Thread
    private var multiplicator : Boolean = false
    private lateinit var scoremultthread : Thread


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
            Thread.sleep((5000..9000).random().toLong())
            multiplicator = false

        }

        scoremultthread = Thread {
            while(multiplicator){
                score++
                Thread.sleep(500)
            }
        }
        scoremultiplicator.start()
        scoremultthread.start()
    }
}

