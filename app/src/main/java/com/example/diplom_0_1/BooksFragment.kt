package com.example.diplom_0_1

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.children
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BooksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val PICK_ANY_FILE = 1
    private lateinit var textViewPlain: TextView
    private lateinit var linearLayout: LinearLayout
    private val books: MutableList<BookAnnotationView> = mutableListOf()
    private var booksStr: MutableList<String>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
//        if (savedInstanceState != null) {
//            val str = savedInstanceState.getStringArray("str")
//            booksStr = str?.toMutableList()
//            booksStr?.forEach { "books: " + println(it) }
//        } else {
//            println("books are null")
//        }
//        println("On create")
        //textViewPlain.text = "PLAIN TEXT"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        outState.putStringArray("str", booksStr?.toTypedArray())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("On destroyView")
    }

    override fun onResume() {
        super.onResume()
        //linearLayout.removeAllViews()
       // books.forEach { linearLayout.addView(it) }
        //linearLayout.children.forEach { println("!!!!  " + it.toString() + "  !!!!") }
        println("On resume")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("On createView")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_books, container, false)

        linearLayout = view.findViewById(R.id.linearLayoutInScrollView)
        //textViewPlain = view.findViewById(R.id.textViewPlain)

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                if(uri != null){
                    BookReader.setCurrentBook(uri, context)
                    val bookAnnotation = BookReader.getBookAnnotation()
                    if (bookAnnotation != null) {
                        println("A: " + bookAnnotation.authorName + ", B: " + bookAnnotation.bookName)
                        val bookAnnottationView = BookAnnotationView(bookAnnotation, context)
                        bookAnnottationView.setOnClickListener{
                            view.findNavController().navigate(R.id.action_booksFragment_to_readingFragment)
                        }
                        linearLayout.addView(bookAnnottationView)
                        saveBook(bookAnnotation)
                    }
                } else {

                }
            } else {

            }
        }

        getBookAnnotations()?.forEach {
            val b = BookAnnotationView(it, context)
            val arr = arrayOf(it.authorName, it.bookName)
            b.setOnClickListener{
                view.findNavController().navigate(R.id.action_booksFragment_to_readingFragment)
            }
            linearLayout.addView(b)
        }
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            // Add MIME type
            intent.type = "*/*"
            launcher.launch(intent)
        }

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BooksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BooksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}