package com.example.diplom_0_1.db

import android.net.Uri
import com.example.diplom_0_1.book.BookAnnotation
import com.example.diplom_0_1.test.Word

//CREATE TABLE Books (id INTEGER PRIMARY KEY AUTOINCREMENT, uri TEXT UNIQUE, name TEXT, author TEXT)")
object BookDAO {

    @JvmStatic
    fun getAllBooks() : List<BookAnnotation> {
        val books = mutableListOf<BookAnnotation>()
        val db = DBUtils.getDataBase().readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Books", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val uri = cursor.getString(1)
            val name = cursor.getString(2)
            val author = cursor.getString(3)
            val book = BookAnnotation(id, name, author, Uri.parse(uri))
            books.add(book)
        }
        cursor.close()
        db.close()
        return books
    }
}