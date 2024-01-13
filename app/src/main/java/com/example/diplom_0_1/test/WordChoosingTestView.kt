package com.example.diplom_0_1.test

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.size


class WordChoosingTestView(val word : Word, answers : List<String>, context: Context?) : LinearLayout(context) {

    private val COLUMNS_COUNT = 3
    private val guessedButton = Button(context)
    private val answersButtons = mutableListOf<TextView>()

    init {
        orientation = VERTICAL
        val border = GradientDrawable()
        border.setColor(-0x1)
        border.setStroke(3, -0x1000000)

        background = border
        guessedButton.setText(word.firstWord)
        //guessedTextView.background = border
        guessedButton.setPadding(15, 15, 15, 15)
        guessedButton.textSize = 20F
        addView(guessedButton)

        val tableLayout = TableLayout(context)
        tableLayout.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        var i = 0
        var tableRow = TableRow(context)
        tableRow.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        answers.forEach {
            val tw = Button(context)
            tw.setOnClickListener {
                checkAnswer(it as TextView)
            }
            tw.setPadding(15, 15, 15, 15)
            //tw.background = border
            tw.textSize = 20F
            //val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            //lp.setMargins(10, 10, 10, 10)
           // tw.layoutParams = lp
            //tw.setBackgroundColor(Color.GRAY)
            tw.setText(it)
            answersButtons.add(tw)
            if (i < COLUMNS_COUNT) {
                tableRow.addView(tw)
            } else {
                tableLayout.addView(tableRow)
                tableRow = TableRow(context)
                tableRow.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                tableRow.addView(tw)
                answersButtons.add(tw)
                i = 0
            }
            ++i
        }
        if (tableRow.size > 0) {
            tableLayout.addView(tableRow)
        }
        addView(tableLayout)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    private fun checkAnswer(clickedTextView: TextView) {
        if (clickedTextView.text.toString().lowercase() == word.secondWord.lowercase()) {
            clickedTextView.setTextColor(Color.BLUE)
            guessedButton.setTextColor(Color.BLUE)
            guessedButton.isClickable = false
            answersButtons.forEach { it.isClickable = false }
        } else {
            clickedTextView.setTextColor(Color.RED)
            guessedButton.setTextColor(Color.RED)
        }
    }
}