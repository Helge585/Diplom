package com.example.diplom_0_1.setting

import android.graphics.Paint
import android.util.DisplayMetrics
import android.util.Log

object SettingsUtils {
    @JvmStatic
    private var letterSize = 42F
    @JvmStatic
    private var displayWidthPx = 0
    @JvmStatic
    private var displayHeightPx = 0
    @JvmStatic
    private var letterCount = 0
    @JvmStatic
    private var displayMetrics : DisplayMetrics? = null
    @JvmStatic
    fun getLetterCount() : Int {
        return letterCount
    }
    @JvmStatic
    fun setDisplayMetrics(_displayWidthPx : Int, _displayHeightPx : Int, dm : DisplayMetrics) {
        displayWidthPx = _displayWidthPx
        displayHeightPx = _displayHeightPx
        displayMetrics = dm
        updateLetterCount()
    }
    @JvmStatic
    fun setLetterSize(_letterSize : Float) {
        letterSize = _letterSize
        updateLetterCount()
    }
    @JvmStatic
    fun getLetterSize() = letterSize
    @JvmStatic
    fun updateLetterCount() {
        val paint = Paint()
        paint.textSize = letterSize
        val fontMetrics = paint.fontMetrics
        val letterHeightPx = fontMetrics.bottom - fontMetrics.top
        val letterWidthPx = paint.measureText("A")
        val line = displayWidthPx / letterWidthPx
        val str = displayHeightPx / letterHeightPx
        letterCount = (line * str).toInt()
    }

    fun getFontSize(): Float {
        return letterSize / displayMetrics?.density!!
    }
}