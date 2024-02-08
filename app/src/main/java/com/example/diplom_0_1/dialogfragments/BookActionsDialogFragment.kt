package com.example.diplom_0_1.dialogfragments

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.diplom_0_1.R
import com.example.diplom_0_1.book.BookReader

class BookActionsDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val uri = Uri.parse(arguments?.getString("uri"))
        val page = arguments?.getInt("page") ?: 0
        val bookId = arguments?.getInt("bookId") ?: -1
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("")
                .setPositiveButton("Открыть") { dialog, id ->
                    BookReader.setCurrentBook(uri, page, context, bookId)
                    findNavController().navigate(R.id.action_booksFragment_to_readingFragment)
                }
                .setNegativeButton("Удалить") { dialog, id ->

                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}