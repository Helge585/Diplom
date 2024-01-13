package com.example.diplom_0_1.test

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.example.diplom_0_1.R


//class WordView @JvmOverloads constructor(
//    val word: Word, context: Context?, attrs: AttributeSet? = null
//) : LinearLayout(context, attrs) {
class WordView(val word : Word, context: Context?) : LinearLayout(context) {

    private val firstWord : EditText = EditText(context)
    private val secondWord : EditText = EditText(context)
    private val changeButton : Button = Button(context)
    private val deleteButton : Button = Button(context)

    init {
        orientation = HORIZONTAL
        //background = resources.getDrawable(R.drawable.border, null)
        val border = GradientDrawable()
        border.setColor(-0x1)
        border.setStroke(3, -0x1000000)
        background = border


        firstWord.setTextColor(resources.getColor(R.color.black))
        secondWord.setTextColor(resources.getColor(R.color.black))
        firstWord.setText(word.firstWord)
        secondWord.setText(word.secondWord)
        setEditableFlag(false)
        val layoutEditTexts = LinearLayout(context)
        layoutEditTexts.orientation = VERTICAL
        layoutEditTexts.addView(firstWord)
        layoutEditTexts.addView(secondWord)

        changeButton.setText("Изменить")
        deleteButton.setText("Удалить")
        val layoutButtons = LinearLayout(context)
        layoutButtons.orientation = VERTICAL
        layoutButtons.addView(changeButton)
        layoutButtons.addView(deleteButton)

//        layoutEditTexts.weightSum = 1F
//        layoutButtons.weightSum = 1F
//        layoutEditTexts.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
//        layoutButtons.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        addView(layoutEditTexts)
        addView(layoutButtons)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    private fun setEditableFlag(flag : Boolean) {
        firstWord.isEnabled = flag
        secondWord.isEnabled = flag
    }

}