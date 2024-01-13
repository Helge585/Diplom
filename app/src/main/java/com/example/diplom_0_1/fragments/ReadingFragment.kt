package com.example.diplom_0_1.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom_0_1.book.BookReader
import com.example.diplom_0_1.book.BookReadingFragmentRecyclerAdapter
import com.example.diplom_0_1.MainActivity
import com.example.diplom_0_1.R


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
        recyclerView= view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = BookReadingFragmentRecyclerAdapter(BookReader.getBookBodyParagraphesList(), this)
        Log.i("Reading fragment", "on create view")
        (activity as MainActivity).setOnReadingFragment(this)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
    public fun sendTranslatedWordToMainActivity(word : String) {
        (activity as MainActivity).OnRecievedTranslatedWordFromReadingFragment(word)
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