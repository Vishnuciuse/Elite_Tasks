package com.example.myapplication.data

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import com.google.android.material.divider.MaterialDivider


class DottedDivider : MaterialDivider {
    private var paint: Paint? = null

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        init()
    }

    private fun init() {
        paint = Paint()
        paint!!.setColor(resources.getColor(R.color.black)) // Set color to black
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeWidth = 2f // Set the stroke width as needed
        paint!!.isAntiAlias = true
        paint!!.setPathEffect(DashPathEffect(floatArrayOf(10f, 10f), 0f)) // Set dash pattern
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawLine(
            0f, (height / 2).toFloat(), width.toFloat(), (height / 2).toFloat(),
            paint!!
        ) // Draw a horizontal line
    }
}

