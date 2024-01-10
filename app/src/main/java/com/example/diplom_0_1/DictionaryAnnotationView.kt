package com.example.diplom_0_1

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController

class DictionaryAnnotationView @JvmOverloads constructor(
    val dictId : Int, val name : String, context: Context?, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val nameView : TextView = TextView(context)
    private val btAdd : Button = Button(context)
    private val btOpen : Button = Button(context)
    private val btTest : Button = Button(context)

    init {
//        btOpen.setOnClickListener {
//            findNavController().navigate(R.id.action_dictionariesFragment_to_dictionaryEditingFragment)
//        }

        nameView.setText(name)
        btAdd.setText("Удалить")
        btOpen.setText("Открыть")
        btTest.setText("Tест")

        orientation = HORIZONTAL
        addView(nameView)
        addView(btAdd)
        addView(btOpen)
        addView(btTest)
    }

    fun getOpenButton() : Button {
        return btOpen
    }

    fun getTestButton() : Button {
        return btTest
    }
}