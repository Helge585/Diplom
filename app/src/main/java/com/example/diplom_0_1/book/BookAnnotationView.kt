package com.example.diplom_0_1.book

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.diplom_0_1.book.BookAnnotation


class BookAnnotationView @JvmOverloads constructor(
    val bookAnnotation: BookAnnotation, context: Context?, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val twBookName: TextView = TextView(context)
    private val twAuthorName = TextView(context)
    private val btStart: Button = Button(context)
    private val verticalLayout = LinearLayout(context)
    //private val btStart: ImageButton = ImageButton(context)

    init {
        orientation = HORIZONTAL
        twBookName.setText(bookAnnotation.bookName)
        twAuthorName.setText(bookAnnotation.authorName)
        btStart.setText("R")

        verticalLayout.orientation = VERTICAL
        verticalLayout.addView(twAuthorName)
        verticalLayout.addView(twBookName)

        addView(btStart)
        addView(verticalLayout)
    }

//    fun setWidgets(_bookAnnotation: BookAnnotation) {
//        orientation = HORIZONTAL
//
////        btStart.layoutParams.width = 50
////        btStart.layoutParams.height = 50
////        btStart.setLayoutParams(LayoutParams(100, 100))
////        btStart.setImageResource(R.drawable.book)
//        //setBackgroundColor(resources.getColor(androidx.transition.R.color.material_blue_grey_800))
//
////        btStart.setOnClickListener {
////            View.findNavController().navigate(R.id.action_booksFragment_to_readingFragment)
////        }
//
//        twBookName.setText(bookAnnotation.bookName)
//        twAuthorName.setText(bookAnnotation.authorName)
//        //twName.setTextColor(resources.getColor(R.color.white))
//        btStart.setText("R")
//
//        verticalLayout.orientation = VERTICAL
//        verticalLayout.addView(twAuthorName)
//        verticalLayout.addView(twBookName)
//
//        addView(btStart)
//        addView(verticalLayout)
//    }
}