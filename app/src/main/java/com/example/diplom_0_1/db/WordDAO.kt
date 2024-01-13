package com.example.diplom_0_1.db

import android.content.ContentValues
import com.example.diplom_0_1.test.Word

//TABLE Words(id INTEGER PRIMARY KEY AUTOINCREMENT, dictId INTEGER, first TEXT, second TEXT)

object WordDAO {
    @JvmStatic fun getAllWords() : List<Word> {
        val words = mutableListOf<Word>()
        val db = DBUtils.getDataBase().readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Words", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val dictId = cursor.getInt(1)
            val firstWord = cursor.getString(2)
            val secondWord = cursor.getString(3)
            val word = Word(id, dictId, firstWord, secondWord)
            words.add(word)
        }
        cursor.close()
        db.close()
        return words
    }

    @JvmStatic fun getAllWordsByDictionaryId(dictId : Int) : List<Word> {
        val words = mutableListOf<Word>()
        val db = DBUtils.getDataBase().readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Words WHERE dictId = $dictId", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val dictId = cursor.getInt(1)
            val firstWord = cursor.getString(2)
            val secondWord = cursor.getString(3)
            val word = Word(id, dictId, firstWord, secondWord)
            words.add(word)
        }
        cursor.close()
        db.close()
        return words
    }

    @JvmStatic
    fun saveWordByDictName(dictName : String, firstWord : String, secondWord : String) {
        val dictId = DictionaryDAO.getDictionaryByName(dictName).id
        val db = DBUtils.getDataBase().writableDatabase
        val cv = ContentValues()
        cv.put("dictId", dictId)
        cv.put("first", firstWord)
        cv.put("second", secondWord)
        db.insert("Words", null, cv)
        db.close()
    }

    @JvmStatic
    fun saveWordByDictId(dictId : Int, firstWord : String, secondWord : String) {
        val db = DBUtils.getDataBase().writableDatabase
        val cv = ContentValues()
        cv.put("dictId", dictId)
        cv.put("first", firstWord)
        cv.put("second", secondWord)
        db.insert("Words", null, cv)
        db.close()
    }
}