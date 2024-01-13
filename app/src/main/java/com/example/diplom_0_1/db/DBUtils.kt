package com.example.diplom_0_1.db

import android.content.Context

object DBUtils {
    @JvmStatic
    private lateinit var db : DBManager

    @JvmStatic
    fun initDataBase(context: Context?) {
        db = DBManager(context)
    }

    @JvmStatic
    fun getDataBase() : DBManager {
        return db
    }
}