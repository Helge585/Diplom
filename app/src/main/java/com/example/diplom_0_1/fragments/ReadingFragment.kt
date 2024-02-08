package com.example.diplom_0_1.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom_0_1.MainActivity
import com.example.diplom_0_1.R
import com.example.diplom_0_1.book.BookReader
import com.example.diplom_0_1.book.BookReadingFragmentRecyclerAdapter

class ReadingFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var nextButton : Button
    private lateinit var prevButton : Button
    lateinit var pagesCountView : TextView

    private var allCount : Int = 0
    private var currentNumber : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reading, container, false)

        pagesCountView = view.findViewById(R.id.pagesCount)
        nextButton = view.findViewById(R.id.nextButton)
        prevButton = view.findViewById(R.id.prevButton)
        recyclerView= view.findViewById(R.id.recyclerView)
        val pages = BookReader.getBookBodyPagesList()
        allCount = pages.size
        currentNumber = BookReader.getPage()
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = BookReadingFragmentRecyclerAdapter(pages, this)
        recyclerView.scrollToPosition(currentNumber)
        pagesCountView.setText("Страница: ${ currentNumber + 1} / ${ allCount + 1}")
        Log.i("Reading fragment", "on create view")
        setSuppressLayoutFlag(true)
        (activity as MainActivity).bottomNavView.isVisible = false

        nextButton.setOnClickListener {
            if (currentNumber < allCount) {
                ++currentNumber
                setSuppressLayoutFlag(false)
                recyclerView.scrollToPosition(currentNumber)
                setSuppressLayoutFlag(true)
                pagesCountView.setText("Страница: ${ currentNumber + 1} / ${ allCount + 1}")
                BookReader.updatePage(currentNumber)
            }
        }
        prevButton.setOnClickListener {
            if (currentNumber > 0) {
                --currentNumber
                setSuppressLayoutFlag(false)
                recyclerView.scrollToPosition(currentNumber)
                setSuppressLayoutFlag(true)
                pagesCountView.setText("Страница: ${ currentNumber + 1} / ${ allCount + 1}")
                BookReader.updatePage(currentNumber)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        BookReader.updatePage(currentNumber)
        (activity as MainActivity).translatingFragment?.translatedTextView?.setText("")
        (activity as MainActivity).bottomNavView.isVisible = true
        Log.i("Reading fragment", "on destroy view")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i("Reading fragment", "on destroy")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Reading fragment", "on stop")
    }
    public fun sendTranslatedWordToMainActivity(word: String, sentence: String) {
        (activity as MainActivity).updateTranslatingFragment(word, sentence)
    }

    public fun setSuppressLayoutFlag(flag : Boolean) {
        recyclerView.suppressLayout(flag)
    }
}