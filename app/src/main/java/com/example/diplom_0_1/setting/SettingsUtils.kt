package com.example.diplom_0_1.setting

import android.graphics.Paint
import android.util.DisplayMetrics
import android.util.Log
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
    var readingLang = "en"
    var translatingLang = "ru"
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
    @JvmStatic
    fun getLetterSizeSp(): Float {
        return letterSizePx / displayMetrics?.density!!
    }
    @JvmStatic
    fun setTheme(themeStr: String) {
        theme = when(themeStr) {
            "red" -> {
                R.style.RedTheme
            }
            "gray" -> {
                R.style.GrayTheme_
            }
            "blue" -> {
                R.style.BlueTheme
            }
            else -> {
                R.style.Base_Theme_Diplom_0_1
            }
        }
    }
    @JvmStatic
    fun getTheme() = theme
}