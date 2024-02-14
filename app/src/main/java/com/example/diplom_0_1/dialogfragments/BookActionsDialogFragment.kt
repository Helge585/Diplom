package com.example.diplom_0_1.dialogfragments

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.diplom_0_1.R
import com.example.diplom_0_1.book.BookAnnotationView
import com.example.diplom_0_1.book.BookReader
import com.example.diplom_0_1.db.BookDAO
import com.example.diplom_0_1.fragments.BooksFragment
import java.lang.Exception

class BookActionsDialogFragment(val booksFragment: BooksFragment) : DialogFragment() {

    var bw : BookAnnotationView? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val uri = Uri.parse(arguments?.getString("uri"))
        val page = arguments?.getInt("page") ?: 0
        val bookId = arguments?.getInt("bookId") ?: -1
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("")
                .setPositiveButton("Открыть") { dialog, id ->
                    try {
                        BookReader.setCurrentBook(uri, 0, context)
                    } catch (e : Exception) {
                        Toast.makeText(context,
                            HtmlCompat.fromHtml("<font color='red'>Не удалось открыть книгу</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                            Toast.LENGTH_LONG).show()
                        Log.i("BooksFragment", e.toString())
                        return@setPositiveButton
                    }
                    findNavController().navigate(R.id.action_booksFragment_to_readingFragment)
                }
                .setNegativeButton("Удалить") { dialog, id ->
                    BookDAO.deleteById(bookId)
                    booksFragment.deleteBook(bw!!)
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}