package com.example.diplom_0_1.book

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.diplom_0_1.R
import com.example.diplom_0_1.book.BookAnnotation


class BookAnnotationView @JvmOverloads constructor(
    val bookAnnotation: BookAnnotation, context: Context?, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val twBookName: TextView = TextView(context)
    private val twAuthorName = TextView(context)
    private val imageView: ImageView = ImageView(context)
    private val verticalLayout = LinearLayout(context)
    //private val btStart: ImageButton = ImageButton(context)

    init {
        orientation = HORIZONTAL
        twBookName.setText(bookAnnotation.bookName)
        twAuthorName.setText(bookAnnotation.authorName)
        imageView.setImageResource(R.drawable.book)
        val lp = LayoutParams(100, 100)
        lp.weight = 0F
        imageView.layoutParams = lp

        verticalLayout.orientation = VERTICAL
        verticalLayout.addView(twAuthorName)
        verticalLayout.addView(twBookName)
        val lp2 = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        lp2.weight = 1F

        addView(imageView)
        addView(verticalLayout)
        setPadding(50, 30, 50, 30)
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