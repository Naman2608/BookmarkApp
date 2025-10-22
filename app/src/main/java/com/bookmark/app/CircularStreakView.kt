package com.bookmark.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class CircularStreakView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress: Float = 0f
    private var streakLabel: String = "D0"
    private var streakColor: Int = ContextCompat.getColor(context, R.color.streak_red)
    
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = ContextCompat.getColor(context, R.color.streak_dark_gray)
    }
    
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        strokeCap = Paint.Cap.ROUND
        color = ContextCompat.getColor(context, R.color.streak_red)
    }
    
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.text_primary_dark)
        textAlign = Paint.Align.CENTER
        textSize = 24f
        isFakeBoldText = true
    }
    
    private val rectF = RectF()
    
    fun setProgress(progress: Float) {
        this.progress = progress.coerceIn(0f, 1f)
        invalidate()
    }
    
    fun setStreakLabel(label: String) {
        this.streakLabel = label
        invalidate()
    }
    
    fun setStreakColor(colorResId: Int) {
        this.streakColor = ContextCompat.getColor(context, colorResId)
        progressPaint.color = streakColor
        invalidate()
    }
    
    fun setStreakData(label: String, progress: Float, colorResId: Int) {
        this.streakLabel = label
        this.progress = progress.coerceIn(0f, 1f)
        this.streakColor = ContextCompat.getColor(context, colorResId)
        progressPaint.color = streakColor
        invalidate()
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        val size = Math.min(width, height).toFloat()
        val padding = backgroundPaint.strokeWidth / 2
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (size / 2f) - padding
        
        rectF.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
        
        // Draw background circle
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)
        
        // Draw progress arc
        if (progress > 0) {
            val sweepAngle = 360f * progress
            canvas.drawArc(rectF, -90f, sweepAngle, false, progressPaint)
        }
        
        // Draw streak label text
        val textY = centerY - ((textPaint.descent() + textPaint.ascent()) / 2)
        canvas.drawText(streakLabel, centerX, textY, textPaint)
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredSize = 120 // dp
        val size = (desiredSize * resources.displayMetrics.density).toInt()
        
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        
        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> Math.min(size, widthSize)
            else -> size
        }
        
        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> Math.min(size, heightSize)
            else -> size
        }
        
        setMeasuredDimension(width, height)
    }
}
