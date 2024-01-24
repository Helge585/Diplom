package com.example.diplom_0_1.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager(
    context: Context?,
    name: String? = "test16.db",
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 1
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Books (id INTEGER PRIMARY KEY AUTOINCREMENT, uri TEXT UNIQUE, name TEXT, author TEXT, page INTEGER)")
        db?.execSQL("CREATE TABLE Dictionaries (id INTEGER PRIMARY KEY AUTOINCREMENT, bookId INTEGER, name TEXT)")
        db?.execSQL("CREATE TABLE Words(id INTEGER PRIMARY KEY AUTOINCREMENT, dictId INTEGER, first TEXT, " +
                "second TEXT, isGuessed INTEGER, guessedRank INTEGER, example TEXT, firstLangId INTEGER, secondLangId INTEGER)")
        db?.execSQL("CREATE TABLE Tests(id INTEGER PRIMARY KEY AUTOINCREMENT, dictId INTEGER," +
                "testTypeId INTEGER, lastResult REAL, averageResult REAL, lastDate TEXT)")
        db?.execSQL("CREATE TABLE TestTypes(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)")
        db?.execSQL("CREATE TABLE Language(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)")

        db?.execSQL("INSERT INTO Dictionaries VALUES(1, 1, 'Default')")
        db?.execSQL("INSERT INTO Language VALUES(1, 'en'),(2, 'ru')")
        db?.execSQL("INSERT INTO TestTypes VALUES(1, 'WritingFirstTest'),(2, 'WritingSecondTest')," +
                "(3, 'ChoosingFirstTest'),(4, 'ChoosingSecondTest')")
        db?.execSQL("INSERT INTO Tests VALUES(1, 1, 1, 0, 0, '---'), (2, 1, 2, 0, 0, '---'), " +
                "(3, 1, 3, 0, 0, '---'), (4, 1, 4, 0, 0, '---')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
//        onCreate(db)
    }

}