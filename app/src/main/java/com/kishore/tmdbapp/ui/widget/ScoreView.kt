package com.kishore.tmdbapp.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.getFloatOrThrow
import com.kishore.tmdbapp.R
import com.kishore.tmdbapp.utils.ofPercent
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class ScoreView : View {

    private var mRadius by Delegates.notNull<Int>()
    private var mPercentage: Double? = null
    private var mPercentageColor: Int = R.color.scoreview_unavailable
    private var mFontSize: Float = 0f
    private val mDifferenceInRadius: Float = 10f
    private val mStartAngleDefault: Float = 270f
    private var mArcRectF: RectF = RectF()
    private var mSweepAngles: Pair<Double, Double> = Pair(0.0, 0.0)
    private val mAvailableRoomInPercentage = 75

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet? = null) {
        attrs?.let { attr ->
            context.theme.obtainStyledAttributes(attr, R.styleable.ScoreView, 0, 0).apply {
                setPercentage(getFloatOrThrow(R.styleable.ScoreView_percentage).toDouble())
            }
        }
    }

    private fun computePercentageColor() {
        mPercentageColor = mPercentage?.let { value ->
            val percentage = value * 10
            when (percentage.roundToInt()) {
                in 1..39 -> R.color.scoreview_below_average
                in 40..69 -> R.color.scoreview_average
                in 70..100 -> R.color.scoreview_above_average
                else -> R.color.scoreview_unavailable
            }
        } ?: R.color.scoreview_unavailable
    }

    private fun computeSweepAngles() {
        mSweepAngles = mPercentage?.let { value ->
            val percentage = value * 10
            val sweepAngle1 = 360.ofPercent(percentage = percentage.toInt())
            var sweepAngle2 = 360 - sweepAngle1
            (if (sweepAngle2 <= 0) {
                sweepAngle2
            } else {
                sweepAngle2.unaryMinus()
            }).also { sweepAngle2 = it }
            Pair(sweepAngle1, sweepAngle2)
        } ?: Pair(0.0, 0.0)
    }

    private fun computeFontSize(text: String) {
        val textBound = Rect()
        val textContainer = getAvailableRoomForText()
        val paint = Paint().apply { textAlign = Paint.Align.CENTER }
        var stage = 1
        var fontSize = 0f
        while (stage < 3) {
            if (stage == 1) {
                fontSize += 10
            } else if (stage == 2) {
                fontSize -= 1
            }

            paint.textSize = fontSize
            paint.getTextBounds(text, 0, text.length, textBound)
            textBound.offsetTo(textContainer.left, textContainer.top)

            val textFitsInView = textContainer.contains(textBound)
            if (stage == 1 && !textFitsInView) stage++
            else if (stage == 2 && textFitsInView) stage++
        }
        mFontSize = fontSize
    }

    private fun getAvailableRoomForText(): Rect {

        val innerCircleRadius = width.div(2f) - (10 + mDifferenceInRadius)
        val percentValue = (innerCircleRadius.toInt().ofPercent(mAvailableRoomInPercentage))
        val leftValue = width.div(2f) - percentValue
        val topValue = height.div(2f) - percentValue
        val rightValue = width.div(2f) + percentValue
        val bottomValue = height.div(2f) + percentValue
        return Rect(
            leftValue.toInt(),
            topValue.toInt(),
            rightValue.toInt(),
            bottomValue.toInt()
        )
    }

    private fun formattedString(): String {
        return mPercentage?.let { value ->
            "%.1f".format(value)
        } ?: "NA"
    }

    private fun onDrawBaseLayout(canvas: Canvas) {
        mArcRectF.set(
            0f,
            0f,
            width.toFloat(),
            height.toFloat()
        )
        canvas.drawArc(
            mArcRectF,
            mStartAngleDefault,
            360f,
            false,
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = ContextCompat.getColor(context, R.color.scoreview_background)
                style = Paint.Style.FILL_AND_STROKE
            })
    }

    private fun onDrawPercentage(canvas: Canvas) {
        computePercentageColor()
        computeSweepAngles()
        mArcRectF.set(
            mDifferenceInRadius,
            mDifferenceInRadius,
            width.toFloat() - mDifferenceInRadius,
            height.toFloat() - mDifferenceInRadius
        )
        canvas.drawArc(
            mArcRectF,
            mStartAngleDefault,
            mSweepAngles.first.toFloat(),
            false,
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = ContextCompat.getColor(context, mPercentageColor)
                style = Paint.Style.STROKE
                strokeCap = Paint.Cap.ROUND
                strokeWidth = 10f
            })
        canvas.drawArc(
            mArcRectF,
            mStartAngleDefault,
            mSweepAngles.second.toFloat(),
            false,
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = ContextCompat.getColor(context, mPercentageColor)
                style = Paint.Style.STROKE
                alpha = 100
                strokeWidth = 10f
            })
    }

    private fun onDrawText(canvas: Canvas) {
        computeFontSize(formattedString())
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL_AND_STROKE
            color = ContextCompat.getColor(context, android.R.color.white)
            strokeCap = Paint.Cap.ROUND
            textSize = mFontSize
        }
        val textRectBounds = getAvailableRoomForText()
        textPaint.getTextBounds(
            formattedString(),
            0,
            formattedString().length,
            textRectBounds
        )

        val xValue = (width.div(2f) - textRectBounds.exactCenterX())
        val yValue = (height.div(2f) - textRectBounds.exactCenterY())

        canvas.drawText(
            formattedString(),
            xValue,
            yValue,
            textPaint
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRadius = min(w, h).div(2)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size =
            min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
        setMeasuredDimension(size, size)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { canvasObj ->
            onDrawBaseLayout(canvasObj)
            onDrawPercentage(canvasObj)
            onDrawText(canvasObj)
        }
    }

    fun setPercentage(percentage: Double) {
        mPercentage = percentage
    }

}