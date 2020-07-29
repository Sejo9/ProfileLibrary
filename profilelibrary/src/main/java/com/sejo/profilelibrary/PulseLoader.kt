package com.sejo.profilelibrary

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class PulseLoader(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val barPath = Path()
    private val barColor: Int
    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG)


    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.PulseLoader, 0, 0)
            .apply {
                barColor = getColor(R.styleable.PulseLoader_barColor, Color.BLUE)
                recycle()
            }

        barPaint.apply {
            color = barColor
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val minw: Int = paddingLeft + paddingRight + MeasureSpec.getSize(widthMeasureSpec)
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)

        val minh: Int = 320 + paddingBottom + paddingTop
        val h: Int = resolveSizeAndState(minh, heightMeasureSpec, 0)

        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawBar(canvas)
    }

    private fun drawBar(canvas: Canvas?) {
        barPath.reset()

        val mWidth = measuredWidth
        val mHeight = measuredHeight

        barPath.moveTo(0f, mHeight * 0.5f)

        barPath.lineTo(mWidth * 0.3f, mHeight * 0.5f)
        barPath.lineTo(mWidth * 0.35f, mHeight * 0.7f)
        barPath.lineTo(mWidth * 0.45f, mHeight * 0.1f)
        barPath.lineTo(mWidth * 0.55f, mHeight * 0.9f)
        barPath.lineTo(mWidth * 0.65f, mHeight * 0.3f)
        barPath.lineTo(mWidth * 0.7f, mHeight * 0.5f)
        barPath.lineTo(mWidth * 1f, mHeight * 0.5f)

        canvas?.drawPath(barPath, barPaint)
    }
}