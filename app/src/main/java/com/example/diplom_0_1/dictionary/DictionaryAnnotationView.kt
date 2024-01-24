package com.example.diplom_0_1.dictionary

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.diplom_0_1.R
import com.example.diplom_0_1.db.DictionaryDAO

class DictionaryAnnotationView @JvmOverloads constructor(
    val dictId : Int, val dictionary: Dictionary, context: Context?, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val btName : TextView = TextView(context)
    //private val btInfo : Button = Button(context)
//    private val btDelete : Button = Button(context)
//    private val btOpen : Button = Button(context)
//    private val btTest : Button = Button(context)

    init {

        val border = GradientDrawable()
        border.setColor(-0x1)
        border.setStroke(3, -0x1000000)

        background = border

        btName.setText("Словарь: " + dictionary.name)
        btName.textSize = 20F
        //btInfo.setText("Инфо")
//        btOpen.setText("Открыть")
//        btTest.setText("Tест")
//        btDelete.setText("Удалить")

//        btDelete.setOnClickListener {
//            DictionaryDAO.deleteById(dictionary.id)
//        }
//        btInfo.setOnClickListener {
//            Log.i("DictionaryAnnotationView", dictionary.toString())
//        }
        setPadding(30, 30, 30 , 30)

        orientation = VERTICAL
        val lin1 = LinearLayout(context)
        lin1.orientation = HORIZONTAL
        val lin2 = LinearLayout(context)
        lin2.orientation = HORIZONTAL

        lin1.addView(btName)
//        lin1.addView(btInfo)

        lin1.gravity = Gravity.CENTER_HORIZONTAL
        lin2.gravity = Gravity.CENTER_HORIZONTAL

//        lin2.addView(btOpen)
//        lin2.addView(btTest)
//        lin2.addView(btDelete)

        addView(lin1)
        //addView(lin2)
    }

//    fun getOpenButton() = btOpen
//    fun getTestButton() = btTest
    //fun getInfoButton() = btInfo
}