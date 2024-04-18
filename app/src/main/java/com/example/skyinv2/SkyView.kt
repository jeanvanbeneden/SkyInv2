package com.example.skyinv2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceView


@SuppressLint("ViewConstructor")
class SkyView(context: Context, screenXParam : Int, screenYParam : Int) : SurfaceView(context), Runnable {
    private var thread: Thread? = null
    private var isPlaying: Boolean
    private var background1: Background
    private var background2: Background
    private val screenX: Int
    private val screenY: Int
    private val player: Player


    //On crée un objet Paint pour dessiner les images de fond.
    private val paint: Paint


    init {
        isPlaying = false
        background1 = Background(screenXParam, screenYParam, resources)
        background2 = Background(screenXParam, screenYParam, resources)
        background2.x = screenXParam
        paint = Paint()
        screenX = screenXParam
        screenY = screenYParam
        player = Player(screenYParam, screenXParam, resources)

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
        thread = Thread(this)
        thread?.start()
    }

    fun pause() {

        try {
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


            //Le player.x et player.y sont les coordonnées du coin supérieur gauche de l'image du joueur.
            canvas.drawBitmap(player.player, player.x.toFloat(), player.y.toFloat(), paint)


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

        background1.x -= 10
        background2.x -= 10

        //Si la première image de fond est complètement hors de l'écran, alors ce sera plus petit que 0
        //On déplace alors la première image de fond à droite de l'écran.
        if (background1.x + background1.background.width < 0) {
            background1.x = screenX
        }

        if (background2.x + background2.background.width < 0) {
            background2.x = screenX
        }

        if (player.up) {
            player.moveUp()
        }

        if (player.down) {
            player.moveDown()
        }

    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                player.up = true
                player.down = false
            }

            MotionEvent.ACTION_UP -> {
                player.down = true
                player.up = false
            }
        }
        return true
    }
}











