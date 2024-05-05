package com.example.skyinv2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceView
import android.graphics.Bitmap
import android.graphics.BitmapFactory

@SuppressLint("ViewConstructor")
class SkyView(context: Context, screenXParam: Int, screenYParam: Int) : SurfaceView(context), Runnable {
    private var thread: Thread? = null
    private var isPlaying: Boolean
    private var background1: Background
    private var background2: Background
    private val screenX: Int
    private val screenY: Int
    private val player: Player
    private var etat : String
    private val paint: Paint
    private var pausebutton : Bitmap
    private val buttonX : Int
    private val buttonY : Int
    private val missile : Missile
    private var numberOfMissiles : Int
    private val missiles : MutableList<Missile>
    private var enemyFollower : FollowerEnemy
    private var straightenemies : MutableList<StraightEnemy>
    private var missiledestruction : MutableList<Missile>
    private var enemydestruction : MutableList<StraightEnemy>
    private var collectabledesctruction : MutableList<Collectable>
    private var collectable: Collectable
    private var score2 :Score
    private var t : Int
    private var tIncrementThread : Thread
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
        numberOfMissiles = 20
        missile = Missile(player.x, player.y, screenY, screenX, resources)
        missiles = mutableListOf()
        enemyFollower = FollowerEnemy(resources)
        straightenemies = mutableListOf()

        missiledestruction = mutableListOf()
        enemydestruction = mutableListOf()
        collectabledesctruction = mutableListOf()

        collectable = Collectable(resources,screenX, screenY)
        score2 = Score()

        t=2
        tIncrementThread = Thread {
            while (isPlaying) {
                Thread.sleep(15000)
                t += 1
            }
        }
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
            sleep(7)
        }
    }

    fun resume() {
        isPlaying = true
        collectable.etat = true
        score2.isPlaying = true
        collectable.collectableFactory()
        thread = Thread(this)
        sleep(1000)
        thread?.start()
        score2.scorecount()
        tIncrementThread.start()

    }

    fun pause() {
        try {
            collectable.etat = false
            isPlaying = false
            score2.isPlaying = false
            thread?.join()

        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun showPausePopup() {
        val pausePopup = PausePopup(context, screenX, screenY)
        pausePopup.show()
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
            paint.textSize = 80f
            canvas.drawText(
                "Score: ${score2.score} ",
                1700f ,
                150f,
                paint
            )
            paint.textSize =40f
            canvas.drawText("Missile : $numberOfMissiles", 1700f, 190f, paint)




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

            for(coinmul in collectable.coinmults){
                canvas.drawBitmap(
                    coinmul.coinmult,
                    coinmul.x.toFloat(),
                    coinmul.y.toFloat(),
                    paint
                )
            }

            for(missileadd in collectable.missileadds){
                canvas.drawBitmap(
                    missileadd.missileadd,
                    missileadd.x.toFloat(),
                    missileadd.y.toFloat(),
                    paint
                )
            }

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

    //La méthode sleep fait dormir le thread pendant 7 millisecondes.
    private fun sleep(t : Int) {
        try {
            Thread.sleep(t.toLong())
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

        if (player.y < -150) {
            player.y = -150
        }
        if (player.y > 750) {
            player.y = 750
        }

        for (missile in missiles) {
            for (enemy in straightenemies) {
                if (missile.interactions(enemy)) {
                    missiledestruction.add(missile)
                    enemydestruction.add(enemy)
                }
            }
            if (missile.interactions(enemyFollower)) {

                missiledestruction.add(missile)
                //il faut créer une liste d'éléments de enemy follower
            }
            if (missile.x >= screenX + 200) {
                missiledestruction.add(missile)
            } else {
                if (missile.active) {
                    missile.move()
                }
            }
        }

        for (objectToDelete in missiledestruction) {
            missiles.remove(objectToDelete)
        }
        missiledestruction.clear()




        if (straightenemies.size < t) {
            val newEnemy = StraightEnemy(resources)
            newEnemy.spawn(screenY, player.y)
            newEnemy.spawn2(screenY, player.y, straightenemies, newEnemy.height)
            newEnemy.x -= newEnemy.speed
            straightenemies.add(newEnemy)
        }

        for (enemy in straightenemies) {
            if (enemy.x < -200) {
                enemydestruction.add(enemy)

            } else {
                enemy.move()
            }
        }


        for (objectToDelete in enemydestruction) {
            straightenemies.remove(objectToDelete)
        }
        enemydestruction.clear()




        if (enemyFollower.x <= -250) {
            enemyFollower.spawn(screenY, player.y)
        }
        //enemyFollower.x -= enemyFollower.speed
        enemyFollower.move()


        for (coinmult in collectable.coinmults) {
            if (coinmult.x <= -200) {
                collectabledesctruction.add(coinmult)
            }
            if (player.interactions(coinmult)) {
                score2.doublescore()
                collectabledesctruction.add(coinmult)
            } else {
                coinmult.move()
            }
        }

        for (objectToDelete in collectabledesctruction) {
            collectable.coinmults.remove(objectToDelete)
        }
        collectabledesctruction.clear()




        for (speedcol in collectable.speedcols){
            if (speedcol.x <= -200){

                collectabledesctruction.add(speedcol)
            }
            if(player.interactions(speedcol)){
                speedcol.speedeffect(straightenemies, enemyFollower, background1, background2)
                collectabledesctruction.add(speedcol)
            }
            else {
                speedcol.move()
            }
        }

        for (objectToDelete in collectabledesctruction) {
            collectable.speedcols.remove(objectToDelete)
        }
        collectabledesctruction.clear()


        for (coin in collectable.coins){
            if (coin.x < 0){
                collectabledesctruction.add(coin)
            }
            if (player.interactions(coin)) {
                score2.scorebonus((50..200).random())
                collectabledesctruction.add(coin)
            }
            else {
                coin.move()
            }
        }

        for (objectToDelete in collectabledesctruction) {
            collectable.coins.remove(objectToDelete)
        }
        collectabledesctruction.clear()


        for (missileadd in collectable.missileadds){
            if (missileadd.x < -200){
                collectabledesctruction.add(missileadd)
            }
            if (player.interactions(missileadd)) {
                numberOfMissiles += 10
                collectabledesctruction.add(missileadd)
            }
            else {
                missileadd.move()
            }
        }

        for (objectToDelete in collectabledesctruction) {
            collectable.missileadds.remove(objectToDelete)
        }
        collectabledesctruction.clear()






    }



    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val touchX = event.x
                val touchY = event.y

                // Vérifie si le toucher est dans les coordonnées du bouton de pause
                if (touchX >= buttonX && touchX <= buttonX + pausebutton.width && touchY >= buttonY && touchY <= buttonY + pausebutton.height) {
                    if (isPlaying) {
                        val sharedPrefs = context.getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
                        val highScore = sharedPrefs.getInt("HighScore", 0)
                        if (score2.score > highScore){
                            val editor = sharedPrefs.edit()
                            editor.putInt("HighScore", score2.score)
                            editor.apply()
                        }

                        pause()  // Met le jeu en pause
                        showPausePopup()
                    } else {
                        resume() // Reprend le jeu
                    }
                    return true
                }

                if(touchX>=1000){
                    if (numberOfMissiles > 0) {
                        numberOfMissiles -= 1
                        missile.x = player.x + 40
                        missile.y =
                            player.y + player.player.height / 2 + missile.missile.height / 2 - 20
                        val newMissile = Missile(
                            missile.x,
                            missile.y,
                            screenX,
                            screenY,
                            resources
                        )
                        missiles.add(newMissile)
                        newMissile.active = true
                    }
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








