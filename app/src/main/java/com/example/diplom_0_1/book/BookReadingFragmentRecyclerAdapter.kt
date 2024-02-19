package com.example.diplom_0_1.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom_0_1.R
import com.example.diplom_0_1.fragments.ReadingFragment
import com.example.diplom_0_1.setting.SettingsUtils

class BookReadingFragmentRecyclerAdapter(private val pages: List<String>, private val fragment: ReadingFragment) :
    RecyclerView.Adapter<BookReadingFragmentRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View, fragment: ReadingFragment) : RecyclerView.ViewHolder(itemView) {
        val textEdit: BookParagraphView = itemView.findViewById(R.id.textViewRecycler)
        init {
            textEdit.setOnFragment(fragment)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView, fragment)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textEdit.setText(pages[position])
        holder.textEdit.textSize = SettingsUtils.getLetterSizeSp()
        //holder.textEdit.textSize = 21F
    }

    override fun getItemCount() = pages.size
}