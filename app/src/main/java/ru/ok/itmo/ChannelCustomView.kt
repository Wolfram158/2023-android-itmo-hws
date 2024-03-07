package ru.ok.itmo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.properties.Delegates

class ChannelCustomView(
    context: Context,
    attributeSet: AttributeSet
): View(context, attributeSet) {
    private val paint = Paint()
    private val TEXT_SIZE = 120f
    private var text = ""
    private var colorOfCircle by Delegates.notNull<Int>()

    init {
        paint.style = Paint.Style.FILL
        colorOfCircle = Color.rgb(
            (0..255).random(),
            (0..255).random(),
            (0..255).random()
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.FILL
        paint.color = colorOfCircle
        canvas.drawCircle(width / 2f, height / 2f, width / 3f, paint)
        paint.color = Color.WHITE
        paint.textSize = TEXT_SIZE
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, width / 2f, height / 2f, paint)
    }

    private fun setText(text: String) {
        this.text = text
        invalidate()
    }

    fun setInitials(input: String?) {
        val factor = input?.split("\\s+".toRegex())
        var text = ""
        if (factor?.size!! > 1) {
            text = (factor[0][0] + factor[1][0].toString()).uppercase()
        } else if (factor.size == 1) {
            text = factor[0][0].toString().uppercase()
        }
        setText(text)
    }

}
