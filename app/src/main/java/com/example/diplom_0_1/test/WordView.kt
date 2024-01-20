package com.example.diplom_0_1.test

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.diplom_0_1.R
import com.example.diplom_0_1.db.WordDAO
import com.example.diplom_0_1.fragments.DictionaryFragment
import org.w3c.dom.Text


//class WordView @JvmOverloads constructor(
//    val word: Word, context: Context?, attrs: AttributeSet? = null
//) : LinearLayout(context, attrs) {
class WordView(val word : Word, context: Context?) : LinearLayout(context) {

    private val firstWord : EditText = EditText(context)
    private val secondWord : EditText = EditText(context)
    private val changeButton : Button = Button(context)
    private val deleteButton : Button = Button(context)

    private val isGuessedText : TextView = TextView(context)
    private val guessedRankText : TextView = TextView(context)

    private var isEditableMode = false

    init {
        orientation = VERTICAL
        //background = resources.getDrawable(R.drawable.border, null)
        val border = GradientDrawable()
        border.setColor(-0x1)
        border.setStroke(3, -0x1000000)
        background = border


        firstWord.setTextColor(resources.getColor(R.color.black))
        secondWord.setTextColor(resources.getColor(R.color.black))
        firstWord.setText(word.firstWord)
        secondWord.setText(word.secondWord)
        firstWord.setSingleLine(true)
        secondWord.setSingleLine(true)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        lp.weight = 1F
        firstWord.layoutParams = lp
        secondWord.layoutParams = lp
        setEditableFlag(false)
        val layoutEditTexts = LinearLayout(context)
        layoutEditTexts.orientation = HORIZONTAL

        val lp2 = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        lp2.weight = 0F
        changeButton.layoutParams = lp2
        deleteButton.layoutParams = lp2
        changeButton.setText("Изменить")
        deleteButton.setText("Удалить")
        val layoutButtons = LinearLayout(context)
        layoutButtons.orientation = HORIZONTAL

        layoutEditTexts.addView(firstWord)
        layoutEditTexts.addView(changeButton)
        layoutButtons.addView(secondWord)
        layoutButtons.addView(deleteButton)
        addView(layoutEditTexts)
        addView(layoutButtons)

        deleteButton.setOnClickListener {
            WordDAO.deleteWordById(word.id)
            firstWord.setText("")
            secondWord.setText("")
            setEditableFlag(false)
            changeButton.isEnabled = false
            deleteButton.isEnabled = false
        }

        changeButton.setOnClickListener {
            if (isEditableMode) {
                var newFirst : String? = null
                var newSecond : String? = null
                isEditableMode = false
                setEditableFlag(false)
                changeButton.setText("Изменить")
                if (word.firstWord != firstWord.text.toString()) {
                    word.firstWord = firstWord.text.toString()
                    newFirst = firstWord.text.toString()
                }
                if (word.secondWord != secondWord.text.toString()) {
                    word.secondWord = secondWord.text.toString()
                    newSecond = secondWord.text.toString()
                }
                if (newFirst != null || newSecond != null) {
                    WordDAO.updateWordFirstSecond(word.id, newFirst, newSecond)
                }
            } else {
                isEditableMode = true
                setEditableFlag(true)
                changeButton.setText("Сохранить")
            }
        }
    }

    private fun setEditableFlag(flag : Boolean) {
        firstWord.isEnabled = flag
        secondWord.isEnabled = flag
    }

}