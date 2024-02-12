package com.example.diplom_0_1.book

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom_0_1.R
import com.example.diplom_0_1.fragments.ReadingFragment

class BookReadingFragmentRecyclerAdapter(private val pages: List<String>, private val fragment: ReadingFragment) :
    RecyclerView.Adapter<BookReadingFragmentRecyclerAdapter.MyViewHolder>() {

    //@SuppressLint("ClickableViewAccessibility")
    inner class MyViewHolder(itemView: View, fragment: ReadingFragment) : RecyclerView.ViewHolder(itemView) {
        val textEdit: BookParagraphView = itemView.findViewById(R.id.textViewRecycler)
        init {
            textEdit.setOnFragment(fragment)
            //textEdit.setOnTouchListener(this)

        }
//        override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
//            (p0 as BookParagraphView).fragment.pagesCountView.setText("Страница: ${adapterPosition + 1} / ${itemCount + 1}")
//            BookReader.updatePage(adapterPosition)
//            //Log.i("BookReadingFragmentRecyclerAdapter", "page = $adapterPosition")
//            return false
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView, fragment)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textEdit.setText(pages[position])
        holder.textEdit.textSize = 21F
        Log.i("Reading Fragment", "!!!123 textSize = " + holder.textEdit.textSize)

        //holder.textEdit.setSelection(0, 5)
    }

    override fun getItemCount() = pages.size
}