package com.example.diplom_0_1.book

import android.net.Uri

class BookAnnotation (val bookId : Int, var bookName : String, var authorName : String, var uri: Uri?, var page : Int){
    override fun toString(): String {
        return "BookAnnotation(bookId=$bookId, bookName='$bookName', authorName='$authorName', uri=$uri, page=$page)"
    }

}