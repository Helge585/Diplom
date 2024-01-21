package com.example.diplom_0_1.db

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.example.diplom_0_1.book.BookAnnotation
import com.example.diplom_0_1.test.Word

//"CREATE TABLE Books (id INTEGER PRIMARY KEY AUTOINCREMENT, uri TEXT UNIQUE, name TEXT, author TEXT, page INTEGER)")
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
            val page = cursor.getInt(4)
            val book = BookAnnotation(id, name, author, Uri.parse(uri), page)
            books.add(book)
        }
        cursor.close()
        db.close()
        Log.i("BookDAO", books[0].toString())
        return books
    }

    @JvmStatic
    fun updatePage(bookId : Int, page : Int) {
        val db = DBUtils.getDataBase().writableDatabase
        val values = ContentValues()
        values.put("page", page)
        val result = db.update("Books", values, "id = ?",
            arrayOf(bookId.toString()))
        Log.i("BookDAO", "bookId=$bookId, page=$page, result=$result")
        db.close()
    }
}