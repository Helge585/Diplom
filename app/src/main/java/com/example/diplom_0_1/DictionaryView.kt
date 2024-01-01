package com.example.diplom_0_1

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class DictionaryView @JvmOverloads constructor(
    val dictId : Int, val name : String, context: Context?, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val nameView : TextView = TextView(context)
    private val btAdd : Button = Button(context)
    private val btOpen : Button = Button(context)
    private val btTest : Button = Button(context)

    init {
        nameView.setText(name)
        btAdd.setText("A")
        btOpen.setText("O")
        btTest.setText("T")

        orientation = HORIZONTAL
        addView(nameView)
        addView(btAdd)
        addView(btOpen)
        addView(btTest)
    }
}