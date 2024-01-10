package com.example.diplom_0_1

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.example.diplom_0_1.DictionaryUtils.Mode


class WordTestView(val word : Word,  context: Context?, val mode : Mode = Mode.WritingFirstTest) : LinearLayout(context) {

    private val firstWord : EditText = EditText(context)
    private val secondWord : EditText = EditText(context)

    init {
        orientation = VERTICAL
        //background = resources.getDrawable(R.drawable.border, null)
        val border = GradientDrawable()
        border.setColor(-0x1)
        border.setStroke(3, -0x1000000)
        background = border

        firstWord.setTextColor(resources.getColor(R.color.black))
        secondWord.setTextColor(resources.getColor(R.color.black))
        firstWord.setSingleLine(true)
        secondWord.setSingleLine(true)
        if (mode == Mode.WritingFirstTest) {
            firstWord.setText("")
            secondWord.setText(word.secondWord)
            secondWord.isEnabled = false
            firstWord.setOnFocusChangeListener { view, b ->
                if (!b) {
                    checkAnswer()
                }
            }
            firstWord.setOnKeyListener { view, i, keyEvent ->
                if (i == KeyEvent.KEYCODE_ENTER) {
                    checkAnswer()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        } else if (mode == Mode.WritingSecondTest) {
            firstWord.setText(word.firstWord)
            secondWord.setText("")
            firstWord.isEnabled = false
            secondWord.setOnFocusChangeListener { view, b ->
                if (!b) {
                    checkAnswer()
                }
            }
            secondWord.setOnKeyListener { view, i, keyEvent ->
                if (i == KeyEvent.KEYCODE_ENTER) {
                    checkAnswer()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        addView(firstWord)
        addView(secondWord)

        //layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    private fun checkAnswer() {
        if (mode == Mode.WritingFirstTest) {
            if (word.firstWord.lowercase() == firstWord.text.toString().lowercase()) {
                firstWord.setTextColor(Color.BLUE)
            } else {
                firstWord.setTextColor(Color.RED)
            }
        } else if (mode == Mode.WritingSecondTest) {
            if (word.secondWord.lowercase() == secondWord.text.toString().lowercase()) {
                secondWord.setTextColor(Color.BLUE)
            } else {
                secondWord.setTextColor(Color.RED)
            }
        }

    }
}