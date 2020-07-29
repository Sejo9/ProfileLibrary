package com.sejo.profilelibrary

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class FillButton(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var backPaint: Paint
    private var frontPaint: Paint
    private var frontRadius: Float = 0f

    init {
        setWillNotDraw(false)

        context.theme.obtainStyledAttributes(attrs, R.styleable.FillButton, 0, 0)
            .apply {
                backPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = getColor(R.styleable.FillButton_backColor, Color.LTGRAY)
                    style = Paint.Style.FILL
                }
                frontPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = getColor(R.styleable.FillButton_frontColor, Color.MAGENTA)
                    style = Paint.Style.FILL
                }
                recycle()
            }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawCircle(width / 2f, height / 2f, width / 2f, backPaint)
        canvas?.drawCircle(width / 2f, height / 2f, frontRadius, frontPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val animator = ValueAnimator.ofFloat(0f, w.toFloat() / 2)
            .apply {
                duration = 3000L
                repeatMode = ValueAnimator.REVERSE
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener {
                    frontRadius = it.animatedValue as Float
                    invalidate()
                }
                startDelay = 1000L
            }
        animator.start()
    }
}