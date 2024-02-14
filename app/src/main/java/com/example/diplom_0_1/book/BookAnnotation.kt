package com.example.diplom_0_1.book

import android.net.Uri

class BookAnnotation (var bookId : Int, var bookName : String, var authorName : String, var uri: Uri?, var page : Int, val image : String = ""){
    override fun toString(): String {
        return "BookAnnotation(bookId=$bookId, bookName='$bookName', authorName='$authorName', uri=$uri, page=$page)"
    }

}