package com.denis.bubbledraw

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@SuppressLint("AppCompatCustomView")
public class BubbleDrawKotlin(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs), View.OnTouchListener {
    val h: Handler = Handler()
    val random = Random
    val bubbleList: MutableList<Bubble> = ArrayList()

    val size = 50
    val delay = 33
    val myPaint = Paint()

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        for ( i in 0 until motionEvent!!.pointerCount){
            var x: Int = motionEvent.getX(i).toInt()
            val y : Int = motionEvent.getY(i).toInt()
            val s = random.nextInt(size) + size;
            bubbleList.add(Bubble(x,y,s))
        }

        return true
    }


    init {
        //testBubbles()
        setOnTouchListener(this)
    }
//    val r: Runnable = Runnable {
//        for (bubble in bubbleList){
//            bubble.update()
//        }
//        invalidate()
//    }
    fun animation(){
        GlobalScope.launch {
            for (bubble in bubbleList){
                bubble.update()
            }
            invalidate()
        }
    }


    override fun onDraw(canvas: Canvas) {
        for (b:Bubble in bubbleList){
            b.draw(canvas)
        }
        animation()
    }
    fun testBubbles(){
        for (i in 1..100){
            val x = random.nextInt(600)
            val y = random.nextInt(600)
            val s = random.nextInt(size) + size
            bubbleList.add(Bubble(x,y,s))

        }
        invalidate()
    }

    inner class Bubble(newX: Int, newY: Int, newSize: Int) {

        var x: Int = newX
        var y: Int = newY
        val size: Int = newSize
        val color: Int
        var xSpeed: Int
        var ySpeed: Int
        val MAX_SPEED = 15

        init {
            color = Color.argb(random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256))

            xSpeed = random.nextInt(MAX_SPEED * 2)- MAX_SPEED
            ySpeed = random.nextInt(MAX_SPEED * 2)- MAX_SPEED
        }
        fun draw(canvas: Canvas){
            myPaint.setColor(color)
            canvas.drawOval(
                (x-size/2).toFloat(), (y-size/2).toFloat(),
                (x+size/2).toFloat(), (y+size/2).toFloat(),myPaint)
        }

        fun update(){
            x += xSpeed
            y += ySpeed
            if (x - size/2 <=0 || x + size/2 >=width)
                xSpeed = -xSpeed
            if ((y - size/2 <=0 || y + size/2 >=height))
                ySpeed = -ySpeed
        }
    }

}