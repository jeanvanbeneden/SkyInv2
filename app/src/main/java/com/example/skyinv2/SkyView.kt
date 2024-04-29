package com.example.skyinv2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceView
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView

@SuppressLint("ViewConstructor")
class SkyView(context: Context, screenXParam: Int, screenYParam: Int) : SurfaceView(context), Runnable {
    private var thread: Thread? = null
    private var isPlaying: Boolean
    private var background1: Background
    private var background2: Background
    private val screenX: Int
    private val screenY: Int
    private val player: Player
    private var score : Int
    private var etat : String
    private val paint: Paint
    private var pausebutton : Bitmap
    private val buttonX : Int
    private val buttonY : Int
    private lateinit var scorethread : Thread
    private val missile : Missile
    private val numberOfMissiles : Int
    private val missiles : MutableList<Missile>
    private var enemyFollower : FollowerEnemy
    private var straightenemies : MutableList<StraightEnemy>
    private var object_destruction : MutableList<Missile>
    private var enemydestruction : MutableList<StraightEnemy>
    private var speedcoldestruction : MutableList<Collectable>
    private var coindestruction : MutableList<Collectable>
    private var collectable: Collectable
    //private val typeface : Typeface
    //private val paintfont : Paint




















    init {
        isPlaying = false
        background1 = Background(screenXParam, screenYParam, resources)
        background2 = Background(screenXParam, screenYParam, resources)
        background2.x = screenXParam
        paint = Paint()
        paint.textSize = 80f
        paint.color = Color.rgb(0, 0, 0 )
        paint.isAntiAlias = true

        screenX = screenXParam
        screenY = screenYParam
        player = Player(screenYParam, screenXParam, resources)
        pausebutton = BitmapFactory.decodeResource(resources, R.drawable.pausebutton)
        val width = pausebutton.width / 10
        val height = pausebutton.height / 10
        pausebutton = Bitmap.createScaledBitmap(pausebutton, width, height, true)
        etat = "Pause"
        buttonX = 100
        buttonY = 50
        score = 0
        numberOfMissiles = 20
        missile = Missile(player.x, player.y, numberOfMissiles , screenY, screenX, resources)
        missiles = mutableListOf()
        enemyFollower = FollowerEnemy(resources)
        straightenemies = mutableListOf()
        object_destruction = mutableListOf()
        enemydestruction = mutableListOf()
        speedcoldestruction = mutableListOf()
        coindestruction = mutableListOf()
        collectable = Collectable(resources,screenX, screenY)

        //typeface = Typeface.createFromAsset(context.assets, "app/res/font/blood_patter.ttf")
        //paintfont = Paint()
        //paintfont.textSize = 80f
        //paintfont.color = Color.rgb(0, 0, 0 )
        //paintfont.isAntiAlias = true
        //paintfont.typeface = typeface


    }

    override fun run() {
        while (isPlaying) {
            update()
            draw()
            sleep()
        }
    }

    fun resume() {
        isPlaying = true
        collectable.etat = true
        collectable.collectableFactory()
        thread = Thread(this)
        thread?.start()

        scorethread = Thread {
            while (isPlaying) {
                Thread.sleep(500) // Une pause de 3 secondes
                score++
            }
        }
        scorethread.start()
    }

    fun pause() {
        try {
            collectable.etat = false
            isPlaying = false
            thread?.join()

        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }


    //La méthode draw dessine les deux images de fond sur le canvas.
    //Le canvas est un objet qui représente la surface de dessin de la vue.
    private fun draw() {
        if (holder.surface.isValid) {

            val canvas = holder.lockCanvas()

            //On dessine les deux images de fond sur le canvas.
            canvas.drawBitmap(
                background1.background,
                background1.x.toFloat(),
                background1.y.toFloat(),
                paint
            )
            canvas.drawBitmap(
                background2.background,
                background2.x.toFloat(),
                background2.y.toFloat(),
                paint
            )
            //paint.typeface = customfont
            canvas.drawText(
                "Score: $score ",
                1700f ,
                150f,
                paint
            )
            canvas.drawBitmap(pausebutton, buttonX.toFloat(), buttonY.toFloat(), paint)   //affiche le logo play/pause cliquable

            //Le player.x et player.y sont les coordonnées du coin supérieur gauche de l'image du joueur.
            canvas.drawBitmap(
                player.player,
                player.x.toFloat(),
                player.y.toFloat(),
                paint
            )

            for (missile in missiles) {
                    canvas.drawBitmap(
                        missile.missile,
                        missile.x.toFloat(),
                        missile.y.toFloat(),
                        paint)
                    }

            for(speedcol in collectable.speedcols){
                canvas.drawBitmap(
                    speedcol.speedcol,
                    speedcol.x.toFloat(),
                    speedcol.y.toFloat(),
                    paint
                )
            }

            for(coin in collectable.coins){
                canvas.drawBitmap(
                    coin.coin,
                    coin.x.toFloat(),
                    coin.y.toFloat(),
                    paint
                )
            }

            //canvas.drawBitmap(
                //collected.speedcol,
                //collected.x.toFloat(),
                //collected.y.toFloat(),
                //paint
            //)

            for (straightEnemy in straightenemies){
                canvas.drawBitmap(
                    straightEnemy.straightEnemy,
                    straightEnemy.x.toFloat(),
                    straightEnemy.y.toFloat(),
                    paint
                )
            }

            canvas.drawBitmap(
                enemyFollower.enemyfollower,
                enemyFollower.x.toFloat(),
                enemyFollower.y.toFloat(),
                paint
            )
            //On dessine le canvas sur la vue.
            //holder est un objet de type SurfaceHolder qui représente la surface de dessin de la vue.
            holder.unlockCanvasAndPost(canvas)
        }
    }

    //La méthode sleep fait dormir le thread pendant 17 millisecondes.
    private fun sleep() {
        try {
            Thread.sleep(7)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }


    //Chaque fois que la méthode update est appelée, on déplace les deux images de fond vers la gauche de 10 pixels.
    private fun update() {

        background1.x -= background1.speed
        background2.x -= background2.speed
        //Si la première image de fond est complètement hors de l'écran, alors ce sera plus petit que 0
        //On déplace alors la première image de fond à droite de l'écran.
        if (background1.x + background1.background.width < 0) {
            background1.x = screenX
        }
        if (background2.x + background2.background.width < 0) {
            background2.x = screenX
        }

        player.move()

        if (player.y <-150){
            player.y = -150
        }
        if (player.y > 750){
            player.y = 750
        }

        for (missile in missiles) {
            if(missile.x >= screenX+200)  {
                object_destruction.add(missile)
            }
            else{
                if(missile.active){
                    missile.move()
                }
            }
        }

        for (objectToDelete in object_destruction) {
            missiles.remove(objectToDelete)
        }
        object_destruction.clear()




        if (straightenemies.size < 2) {
            val newEnemy = StraightEnemy(resources)
            newEnemy.spawn(screenY, player.y)
            newEnemy.spawn2(screenY, player.y, straightenemies, newEnemy.height)
            newEnemy.x -= newEnemy.speed
            straightenemies.add(newEnemy)
        }

        for (enemy in straightenemies){
            if (enemy.x < -200){
                enemydestruction.add(enemy)

            }
            else {
                enemy.move()
            }
        }


        for (objectToDelete in enemydestruction) {
            straightenemies.remove(objectToDelete)
        }
        enemydestruction.clear()




        if (enemyFollower.x < -250){
            enemyFollower.spawn(screenY, player.y)
        }
        //enemyFollower.x -= enemyFollower.speed
        enemyFollower.move()


        for (speedcol in collectable.speedcols){
            if (speedcol.x < -200){
                speedcoldestruction.add(speedcol)
            }
            if(player.interactions(speedcol)){
                score +=40
                speedcol.speedeffect(straightenemies, enemyFollower, background1, background2)

                speedcoldestruction.add(speedcol)
            }
            else {
                speedcol.move()
            }
        }

        for (objectToDelete in speedcoldestruction) {
            collectable.speedcols.remove(objectToDelete)
        }
        speedcoldestruction.clear()


        for (coin in collectable.coins){
            if (coin.x < -200){
                coindestruction.add(coin)
            }
            if (player.interactions(coin)) {
                collectable.coineffect()

                coindestruction.add(coin)
            }
            else {
                coin.move()
            }
        }

        for (objectToDelete in coindestruction) {
            collectable.coins.remove(objectToDelete)
        }
        coindestruction.clear()
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val touchX = event.x
                val touchY = event.y

                // Vérifie si le toucher est dans les coordonnées du bouton de pause
                if (touchX >= buttonX && touchX <= buttonX + pausebutton.width && touchY >= buttonY && touchY <= buttonY + pausebutton.height) {
                    if (isPlaying) {
                        pause()  // Met le jeu en pause
                    } else {
                        resume() // Reprend le jeu
                    }
                    return true
                }

                if(touchX>=1000){
                    //missilactif = true // Active le missile
                    missile.x = player.x + 40
                    missile.y = player.y + player.player.height / 2 + missile.missile.height / 2 - 20
                    val newMissile= Missile(missile.x,missile.y,numberOfMissiles, screenX , screenY, resources )
                    missiles.add(newMissile)
                    newMissile.active = true
                    score -=4
                    }
                else {
                    // Gestion du mouvement du joueur
                    player.up = true
                    player.down = false
                    }
            }

            MotionEvent.ACTION_UP -> {
                player.down = true
                player.up = false
            }
        }
        return true
    }
}








