package com.example.diplom_0_1


private var books: MutableList<BookAnnotation> = mutableListOf()

fun saveBook(bookAnnotation: BookAnnotation) {
    books.add(bookAnnotation)
}

fun getBookAnnotations(): MutableList<BookAnnotation> { return books }
