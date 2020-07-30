package com.sejo.profilelibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class FillButtonJV extends View {

    private Paint backCirclePaint = new Paint();
    private Paint frontCirclePaint = new Paint();
    private float frontRadius;

    public FillButtonJV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FillButton, 0, 0);

        try {
            backCirclePaint.setColor(a.getColor(R.styleable.FillButton_backColor, Color.DKGRAY));
            backCirclePaint.setStyle(Paint.Style.FILL);
            frontCirclePaint.setColor(a.getColor(R.styleable.FillButton_frontColor, Color.BLUE));
            frontCirclePaint.setStyle(Paint.Style.FILL);
        } finally {
            a.recycle();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f, backCirclePaint);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, frontRadius, frontCirclePaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        ValueAnimator animator = ValueAnimator.ofFloat(0, w / 2f);
        animator.setDuration(3000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                frontRadius = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.setStartDelay(1000);
        animator.start();
    }
}
