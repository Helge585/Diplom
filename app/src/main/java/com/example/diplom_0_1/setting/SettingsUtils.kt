package com.example.diplom_0_1.setting

import android.graphics.Paint
import android.util.DisplayMetrics
import com.example.diplom_0_1.R

object SettingsUtils {
    @JvmStatic
    private var letterSizePx = 42F
    @JvmStatic
    private var letterCount = 0
    @JvmStatic
    private var displayMetrics : DisplayMetrics? = null
    @JvmStatic
    val maxLetterSizePx = 100
    @JvmStatic
    val minLetterSizePx = 10
    @JvmStatic
    private var theme = R.style.Base_Theme_Diplom_0_1
    @JvmStatic
    fun getLetterCount() : Int {
        return letterCount
    }
    @JvmStatic
    fun setDisplayMetrics(dm : DisplayMetrics) {
        displayMetrics = dm
        updateLetterCount()
    }
    @JvmStatic
    fun setLetterSizePx(_letterSizePx : Float) {
        letterSizePx = _letterSizePx
        updateLetterCount()
    }
    @JvmStatic
    fun getLetterSizePx() = letterSizePx
    @JvmStatic
    fun updateLetterCount() {
        val paint = Paint()
        paint.textSize = letterSizePx
        val fontMetrics = paint.fontMetrics
        val letterHeightPx = fontMetrics.bottom - fontMetrics.top
        val letterWidthPx = paint.measureText("A")
        val line = displayMetrics?.widthPixels?.div(letterWidthPx)
        val str = displayMetrics?.heightPixels?.div(letterHeightPx)
        if (line != null && str != null) {
            letterCount = (line * str).toInt()
        } else {
            letterCount = 0
        }
    }

    fun getLetterSizeSp(): Float {
        return letterSizePx / displayMetrics?.density!!
    }

    fun setTheme(themeStr: String) {
        theme = when(themeStr) {
            "red" -> {
                R.style.RedTheme
            }
            "green" -> {
                R.style.GreenTheme
            }
            "blue" -> {
                R.style.BlueTheme
            }
            else -> {
                R.style.Base_Theme_Diplom_0_1
            }
        }
    }

    fun getTheme() = theme
}