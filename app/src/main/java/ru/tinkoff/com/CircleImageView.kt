package ru.tinkoff.com

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.CYAN
        style = Paint.Style.FILL_AND_STROKE
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
    }

    private var foregroundDrawable: Drawable? = null
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    private var textSize: Int
        get() = textPaint.textSize.toInt()
        set(value) {
            if (textPaint.textSize.toInt() != value) {
                textPaint.textSize = value.toFloat()
                requestLayout()
            }
        }

    private var text: String = TEXT
        set(value) {
            if (field != value) {
                field = value
                requestLayout()
            }
        }

    private val centerPoint = PointF()
    private val textPoint = PointF()
    private var radius: Float = 0F

    init {
        context.obtainStyledAttributes(attrs, R.styleable.CircleImageView).apply {
            textSize = getDimensionPixelSize(R.styleable.CircleImageView_cl_text_size, context.spT)
            foregroundDrawable = getDrawable(R.styleable.CircleImageView_cl_foreground)
            text = getText(R.styleable.CircleImageView_cl_text)?.toString() ?: TEXT
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val textWidth = textPaint.measureText(TEXT, 0, TEXT.length)
        val textHeight = textPaint.fontMetrics.run { bottom - top}
        val contentWidth = textWidth + paddingStart + paddingEnd
        val contentHeight = textHeight + paddingTop + paddingBottom
        val width = resolveSize(contentWidth.toInt(), widthMeasureSpec)
        val height = resolveSize(contentHeight.toInt(), heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(centerPoint.x, centerPoint.y, radius, circlePaint)
        canvas?.drawText(text, textPoint.x, textPoint.y, textPaint)
    }

    companion object {
        private const val TEXT = "Hi"
        private const val DEFAULT_FONT_SIZE_PX = 14F
    }
}