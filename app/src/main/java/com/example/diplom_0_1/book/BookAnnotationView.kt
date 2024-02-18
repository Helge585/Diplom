package com.example.diplom_0_1.book

import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.util.Base64
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

    init {
        orientation = HORIZONTAL
        twBookName.setText(bookAnnotation.bookName)
        twAuthorName.setText(bookAnnotation.authorName)
        if (bookAnnotation.image.length == 0) {
            imageView.setImageResource(R.drawable.book)
        } else {
            val ba2 = Base64.decode(bookAnnotation.image.toString(), Base64.DEFAULT)
            val bm = BitmapFactory.decodeByteArray(ba2, 0, ba2.size)
            imageView.setImageBitmap(bm)
        }
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
}