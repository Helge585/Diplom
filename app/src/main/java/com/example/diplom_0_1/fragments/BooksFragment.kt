package com.example.diplom_0_1.fragments

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.HtmlCompat
import androidx.navigation.findNavController
import com.example.diplom_0_1.book.BookAnnotationView
import com.example.diplom_0_1.book.BookReader
import com.example.diplom_0_1.R
import com.example.diplom_0_1.db.BookDAO
import com.example.diplom_0_1.dialogfragments.BookActionsDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.Exception

class BooksFragment : Fragment() {
    private lateinit var linearLayout: LinearLayout
    private val bookActionsDialogFragment = BookActionsDialogFragment(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
    override fun onResume() {
        super.onResume()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_books, container, false)
        linearLayout = view.findViewById(R.id.linearLayoutInScrollView)

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                if(uri != null){
                    activity?.contentResolver?.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    try {
                        BookReader.setCurrentBook(uri, 0, context)
                        val bookAnnotation = BookReader.getBookAnnotation()
                        if (bookAnnotation != null) {
                            try {
                                BookDAO.create(bookAnnotation)
                            } catch (e : Exception) {
                                Toast.makeText(context,
                                    HtmlCompat.fromHtml("<font color='red'>Не удалось сохранить книгу</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                                    Toast.LENGTH_LONG).show()
                            }
                            bookAnnotation.bookId = BookDAO.getBookIdByUri(bookAnnotation.uri?.toString() ?: "")
                            val bookAnnottationView = BookAnnotationView(bookAnnotation, context)
                            bookAnnottationView.setOnClickListener{
                                Log.i("BooksFragment", "book on click")
                                val args = Bundle()
                                args.putString("uri", bookAnnotation.uri.toString())
                                args.putInt("page", bookAnnotation.page)
                                args.putInt("bookId", bookAnnotation.bookId)
                                bookActionsDialogFragment.bw = ( it as BookAnnotationView)
                                bookActionsDialogFragment.arguments = args
                                bookActionsDialogFragment.show(activity!!.supportFragmentManager, "bookMenu")
                            }
                            linearLayout.addView(bookAnnottationView, 0)
                        }
                    } catch (e : Exception) {
                        Toast.makeText(context,
                            HtmlCompat.fromHtml("<font color='red'>Не удалось открыть книгу</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                            Toast.LENGTH_LONG).show()
                        Log.i("BooksFragment", e.toString())
                    }
                }
            }
        }

        val books = BookDAO.getAllBooks()
        books.forEach {
            val bw = BookAnnotationView(it, context)
            bw.setOnClickListener {
                val bookAnnotation = (it as BookAnnotationView).bookAnnotation
                val args = Bundle()
                args.putString("uri", bookAnnotation.uri.toString())
                args.putInt("page", bookAnnotation.page)
                args.putInt("bookId", bookAnnotation.bookId)
                bookActionsDialogFragment.bw = ( it as BookAnnotationView)
                bookActionsDialogFragment.arguments = args
                bookActionsDialogFragment.show(activity!!.supportFragmentManager, "bookMenu")
            }
            linearLayout.addView(bw)
            Log.i("Book", bw.bookAnnotation.toString())
        }
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            //MIME type
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/octet-stream", "text/plain"))
            launcher.launch(intent)
        }
        return view
    }
    fun deleteBook(bw : BookAnnotationView) {
        linearLayout.removeView(bw)
    }
}