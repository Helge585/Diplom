package com.example.diplom_0_1.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.diplom_0_1.test.TestUtils
import com.example.diplom_0_1.R
import com.example.diplom_0_1.db.BookDAO
import com.example.diplom_0_1.db.DictionaryDAO

class CreateDictionaryDialogFragment : DialogFragment() {

    private var bookIndex = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dictName = EditText(context)
        dictName.setPadding(30, 10, 30, 10)
        val books = BookDAO.getAllBooks()
        val bookNames = mutableListOf<String>()
        books.forEach {
            bookNames.add(it.bookName)
        }
        if (bookNames.size > 0) {
            dictName.setText(bookNames[0])
        }
        bookNames.add("без книги")
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Новый словарь")
                .setSingleChoiceItems(
                    bookNames.toTypedArray(), 0
                ) { dialog, item ->

                }
                .setView(dictName)
                .setPositiveButton("Выбрать") { dialog, id ->
                    if (bookIndex != -1 && dictName.text.toString() != "") {
                        DictionaryDAO.create(books[bookIndex].bookId, dictName.text.toString())
                    }
                }
                .setNegativeButton("Отменить") { dialog, id ->
                    dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}