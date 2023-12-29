package com.example.diplom_0_1

import android.annotation.SuppressLint
import android.text.Selection
import android.text.Spannable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookReadingFragmentRecyclerAdapter(private val pages: List<String>) :
    RecyclerView.Adapter<BookReadingFragmentRecyclerAdapter.MyViewHolder>() {

    //@SuppressLint("ClickableViewAccessibility")
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textEdit: BookParagraphView = itemView.findViewById(R.id.textViewRecycler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textEdit.setText(pages[position])
        holder.textEdit.setSelection(0, 5)
    }

    override fun getItemCount() = pages.size
}