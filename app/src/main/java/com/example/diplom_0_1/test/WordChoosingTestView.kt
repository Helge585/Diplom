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


class WordChoosingTestView(override val word : Word, answers : List<String>, context: Context?, val mode : TestUtils.Mode) : LinearLayout(context), TestCase {

    private val COLUMNS_COUNT = 3
    private val guessedButton = Button(context)
    private val answersButtons = mutableListOf<Button>()
    private var isRight = false
    private var isTried = false

    init {
        orientation = VERTICAL
        val border = GradientDrawable()
        border.setColor(-0x1)
        border.setStroke(3, -0x1000000)

        background = border
        if (mode == TestUtils.Mode.ChoosingSecondTest) {
            guessedButton.setText(word.firstWord)
        } else {
            guessedButton.setText(word.secondWord)
        }
        guessedButton.setPadding(15, 15, 15, 15)
        guessedButton.textSize = 20F
        addView(guessedButton)

        val tableLayout = TableLayout(context)
        tableLayout.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        var i = 0
        var tableRow = TableRow(context)
        tableRow.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        var _answers = answers
        _answers = _answers.shuffled()
        _answers.forEach {
            val tw = Button(context)
            tw.setOnClickListener {
                checkAnswer(it as Button)
            }
            tw.setPadding(15, 15, 15, 15)
            tw.textSize = 20F
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

    fun checkAnswer(button : Button) {
        val isAnswerRight = if (mode == TestUtils.Mode.ChoosingSecondTest) {
            button.text.toString().lowercase() == word.secondWord.lowercase()
        } else {
            button.text.toString().lowercase() == word.firstWord.lowercase()
        }
        if (isAnswerRight) {
            isRight = true
            button.setTextColor(Color.BLUE)
            guessedButton.setTextColor(Color.BLUE)
            guessedButton.isClickable = false
            answersButtons.forEach { it.isClickable = false }
        } else {
            button.setTextColor(Color.RED)
            guessedButton.setTextColor(Color.RED)
        }
        isTried = true
    }

    override fun isTried(): Boolean {
        return isTried
    }

    override fun isRight(): Boolean {
        return isRight
    }

    override fun getTestType(): TestUtils.Mode {
        return mode
    }
}