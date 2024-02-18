package com.example.diplom_0_1.book

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.example.diplom_0_1.fragments.ReadingFragment

class BookParagraphView : androidx.appcompat.widget.AppCompatEditText {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    lateinit var fragment: ReadingFragment

    fun setOnFragment(fr : ReadingFragment) {
        fragment = fr
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x.toInt()
                val y = event.y.toInt()
                val position = layout.getOffsetForHorizontal(layout.getLineForVertical(y), x.toFloat())
                var start = position
                var end = position
                val text = layout.text.toString()

                while (start > 0 && text[start - 1].isLetter()) {
                    start--
                }
                while (end < text.length && text[end].isLetter()) {
                    end++
                }
                if (start != end) {
                    setSelection(start, end)
                    println("!!!  " + start + " " + end)
                }
                var sentenceStart = start
                var sentenceEnd = end
                var se = arrayListOf<Char>('.', '!', '?')
                while (sentenceStart >= 0 && !(text[sentenceStart] in se)) {
                    --sentenceStart
                }
                while (sentenceEnd < text.length && !(text[sentenceEnd] in se)) {
                    ++sentenceEnd
                }

                fragment.sendTranslatedWordToMainActivity(text.toString().substring(start, end),
                    text.toString().substring(sentenceStart + 1, sentenceEnd))
            }
        }
        return super.onTouchEvent(event)
    }
}