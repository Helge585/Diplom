package com.example.diplom_0_1.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager(
    context: Context?,
    name: String? = "test4.db",
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 1
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Books (id INTEGER PRIMARY KEY AUTOINCREMENT, uri TEXT UNIQUE, name TEXT, author TEXT)")
        db?.execSQL("CREATE TABLE Dictionaries (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)")
        db?.execSQL("CREATE TABLE Words(id INTEGER PRIMARY KEY AUTOINCREMENT, dictId INTEGER, first TEXT, second TEXT)")

        db?.execSQL("INSERT INTO Dictionaries (name) VALUES('Default')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}