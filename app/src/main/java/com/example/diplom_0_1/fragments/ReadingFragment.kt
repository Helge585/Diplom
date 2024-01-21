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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReadingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReadingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var textViewTranslate: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var nextButton : Button
    private lateinit var prevButton : Button
    lateinit var pagesCountView : TextView

    private var allCount : Int = 0
    private var currentNumber : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Log.i("Reading fragment", "on create")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reading, container, false)

        pagesCountView = view.findViewById<TextView>(R.id.pagesCount)
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
        (activity as MainActivity).setOnReadingFragment(this)

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
        (activity as MainActivity).OnRecievedTranslatedWordFromReadingFragment(word, sentence)
    }

    public fun setSuppressLayoutFlag(flag : Boolean) {
        recyclerView.suppressLayout(flag)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReadingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReadingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}