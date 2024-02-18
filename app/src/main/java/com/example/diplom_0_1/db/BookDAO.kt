package com.example.diplom_0_1.db

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.example.diplom_0_1.MainActivity
import com.example.diplom_0_1.book.BookAnnotation
import com.example.diplom_0_1.test.Word

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
            val image = cursor.getString(5)
            val book = BookAnnotation(id, name, author, Uri.parse(uri), page, image)
            books.add(book)
        }
        cursor.close()
        db.close()
        return books
    }
    @JvmStatic
    fun getBookIdByUri(uriStr : String) : Int{
        var result = -1
        val db = DBUtils.getDataBase().readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Books WHERE uri = '$uriStr'", null)
        if (cursor.moveToNext()) {
            result = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return result
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
    @JvmStatic
    fun create(bookAnnotation : BookAnnotation) {
        val db = DBUtils.getDataBase().writableDatabase
        val cv = ContentValues()
        cv.put("uri", bookAnnotation.uri.toString())
        cv.put("name", bookAnnotation.bookName)
        cv.put("author", bookAnnotation.authorName)
        cv.put("page", 0)
        cv.put("image", bookAnnotation.image)
        db.insert("Books", null, cv)
        db.close()
    }
    @JvmStatic
    fun deleteById(bookId : Int) {
        val db = DBUtils.getDataBase().writableDatabase
        db.delete("Books", "id = ?", arrayOf(bookId.toString()))
        db.close()
    }
}