package com.sejo.profilelibrary

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import java.text.NumberFormat

class BalanceMeter(context: Context, attrs: AttributeSet) : View(context, attrs) {

    //Paint objects
    private val meterBackPaint: Paint = Paint()
    private val meterFrontPaint: Paint = Paint()
    private val balanceTextPaint: Paint = Paint()
    private val remTextPaint: Paint = Paint()
    private val limitTextPaint: Paint = Paint()

    //Variables for the height of the remaining amount and it's offset
    private var balanceTextHeight: Float
    private var balanceTextOffset: Float

    //Variable for the Rect containing the arcs
    private var arcRect: RectF = RectF()

    //Variable containing the size of the arcs
    private var mStrokeWidth: Float = 0f

    //Variables for the used amount and daily limit
    private var used: Float = 0f
    private var limit: Float = 0f

    //Variables for remaining amount and it's radius
    private var remainingRadius: Float = 0f
    private var remainingAmount: Float = 0.00f

    //Number Format variable
    private val nf: NumberFormat

    init {
        setWillNotDraw(false)

        //Retrieves values from provided AttributeSet and assigns them to variables
        context.theme.obtainStyledAttributes(attrs, R.styleable.BalanceMeter, 0, 0).apply {
            mStrokeWidth = getDimension(R.styleable.BalanceMeter_meterWidth, 14f)

            meterBackPaint.apply {
                color = getColor(R.styleable.BalanceMeter_meterBackColor, Color.LTGRAY)
                style = Paint.Style.STROKE
                strokeWidth = getDimension(R.styleable.BalanceMeter_meterWidth, 14f)
                strokeCap = Paint.Cap.ROUND
            }

            meterFrontPaint.apply {
                color = getColor(R.styleable.BalanceMeter_meterFrontColor, Color.BLUE)
                style = Paint.Style.STROKE
                strokeWidth = getDimension(R.styleable.BalanceMeter_meterWidth, 14f)
                strokeCap = Paint.Cap.ROUND
            }

            balanceTextPaint.apply {
                color = getColor(R.styleable.BalanceMeter_balanceTextColor, Color.BLACK)
                style = Paint.Style.FILL
                isLinearText = true
                textSize = getDimension(R.styleable.BalanceMeter_balanceTextSize, 70f)
                textAlign = Paint.Align.CENTER
            }

            remTextPaint.apply {
                color = getColor(R.styleable.BalanceMeter_remTextColor, Color.LTGRAY)
                style = Paint.Style.FILL
                isLinearText = true
                textSize = getDimension(R.styleable.BalanceMeter_remTextSize, 60f)
                textAlign = Paint.Align.CENTER
            }

            limitTextPaint.apply {
                color = getColor(R.styleable.BalanceMeter_limitTextColor, Color.BLACK)
                style = Paint.Style.FILL
                isLinearText = true
                textSize = getDimension(R.styleable.BalanceMeter_limitTextSize, 40f)
                textAlign = Paint.Align.CENTER
            }

            recycle() //Recycles provided AttributeSet to free memory
        }

        //Calculates the height of the remaining amount text and its Y offset
        balanceTextHeight = balanceTextPaint.descent() - balanceTextPaint.ascent()
        balanceTextOffset = (balanceTextHeight / 2) - balanceTextPaint.descent()

        //Instantiates number formater for currency values
        nf = NumberFormat.getInstance().apply {
            maximumFractionDigits = 2
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawArc(arcRect, 135f, 270f, false, meterBackPaint)
        canvas?.drawArc(arcRect, 135f, remainingRadius, false, meterFrontPaint)
        canvas?.drawText(
            "₵" + nf.format(remainingAmount),
            width / 2f,
            height / 2f + balanceTextOffset,
            balanceTextPaint
        )
        canvas?.drawText(
            "remaining",
            width / 2f,
            height / 2f + (balanceTextHeight + balanceTextOffset),
            remTextPaint
        )
        canvas?.drawText(
            nf.format(limit) + " GH₵ / day",
            width / 2f,
            arcRect.bottom,
            limitTextPaint
        )
    }

    //Ensure the width and height of view is the same
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minim = measuredWidth.coerceAtMost(measuredHeight)

        setMeasuredDimension(minim, minim)
    }

    //Set the boundaries for the Rect containing the arcs
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        arcRect.set(0 + mStrokeWidth, 0 + mStrokeWidth, w - mStrokeWidth, h - mStrokeWidth)
    }

    //Calculates the sweeping angle for the remaining balance
    private fun calcSweepAngle(remainder: Float, limit: Float): Float {
        return (remainder / limit) * 270
    }

    //Set the amount used and daily limit
    //Set and start the animation for filling the arc
    fun setUsedAndLimit(used: Float, limit: Float) {
        this.used = used
        this.limit = limit

        ValueAnimator.ofFloat(0f, limit - used).apply {
            duration = 3000L //Duration of 3 seconds for animation
            interpolator =
                AccelerateDecelerateInterpolator() //Animation accelerates at beginning and slows down at animation end
            addUpdateListener {
                remainingAmount = it.animatedValue as Float
                remainingRadius = calcSweepAngle(remainingAmount, limit)
                invalidate()
            }
            start()
        }
    }
}