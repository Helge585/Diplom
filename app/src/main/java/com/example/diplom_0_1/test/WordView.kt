package com.example.diplom_0_1.test

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.example.diplom_0_1.R
import com.example.diplom_0_1.db.WordDAO

class WordView(val word : Word, context: Context?) : LinearLayout(context) {

    private val firstWord : EditText = EditText(context)
    private val secondWord : EditText = EditText(context)
    private val changeButton : Button = Button(context)
    private val infoButton : Button = Button(context)

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
        infoButton.layoutParams = lp2
        changeButton.setText("Изменить")
        infoButton.setText("Инфо")
        val layoutButtons = LinearLayout(context)
        layoutButtons.orientation = HORIZONTAL

        layoutEditTexts.addView(firstWord)
        layoutEditTexts.addView(changeButton)
        layoutButtons.addView(secondWord)
        layoutButtons.addView(infoButton)
        addView(layoutEditTexts)
        addView(layoutButtons)


        infoButton.setOnClickListener {
            val exampleDialog = AlertDialog.Builder(context)
            exampleDialog.setTitle(word.firstWord + " " + word.secondWord)
            exampleDialog.setMessage(word.example)
            exampleDialog.setPositiveButton("Ок") {dialog, item ->

            }
            exampleDialog.setNegativeButton("Удалить") { dialog, item ->
                WordDAO.deleteWordById(word.id)
                firstWord.setText("")
                secondWord.setText("")
                setEditableFlag(false)
                changeButton.isEnabled = false
                infoButton.isEnabled = false
                Toast.makeText(context,
                    HtmlCompat.fromHtml("<font color='red'>Слово удалено</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                    Toast.LENGTH_LONG).show()
            }
            exampleDialog.show()
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
                Toast.makeText(context,
                    HtmlCompat.fromHtml("<font color='red'>Слово сохранено</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                    Toast.LENGTH_LONG).show()
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