package com.example.diplom_0_1.db

import android.content.ContentValues
import com.example.diplom_0_1.test.Word

//"CREATE TABLE Words(id INTEGER PRIMARY KEY AUTOINCREMENT, dictId INTEGER, first TEXT, " +
//                "second TEXT, isGuessed INTEGER, guessedRank INTEGER)")

object WordDAO {
    @JvmStatic
    fun deleteWordById(wordId : Int) {
        val db = DBUtils.getDataBase().writableDatabase
        db.delete("Words", "id = ?", arrayOf(wordId.toString()))
        db.close()
    }
    @JvmStatic
    fun updateWordFirstSecond(wordId : Int, newFirst : String?, newSecond : String?) {
        val db = DBUtils.getDataBase().writableDatabase
        val values = ContentValues()
        if (newFirst != null) {
            values.put("first", newFirst)
        }
        if (newSecond != null) {
            values.put("second", newSecond)
        }
        val result = db.update("Words", values, "id = ?",
            arrayOf(wordId.toString()))
        db.close()
    }
    @JvmStatic fun getAllWords() : List<Word> {
        val words = mutableListOf<Word>()
        val db = DBUtils.getDataBase().readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Words", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val dictId = cursor.getInt(1)
            val firstWord = cursor.getString(2)
            val secondWord = cursor.getString(3)
            val isGueseed = cursor.getInt(4)
            val guessedRank = cursor.getInt(5)
            val example = cursor.getString(6)
            val word = Word(id, dictId, firstWord, secondWord, isGueseed, guessedRank, example)
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
            val isGuessed = cursor.getInt(4)
            val guessedRank = cursor.getInt(5)
            val example = cursor.getString(6)
            val word = Word(id, dictId, firstWord, secondWord, isGuessed, guessedRank, example)
            words.add(word)
        }
        cursor.close()
        db.close()
        return words
    }

    @JvmStatic
    fun saveWordByDictName(dictName: String, firstWord: String, secondWord: String, example: String) {
        val dictId = DictionaryDAO.getDictionaryIdByName(dictName)
        val db = DBUtils.getDataBase().writableDatabase
        val cv = ContentValues()
        cv.put("dictId", dictId)
        cv.put("first", firstWord)
        cv.put("second", secondWord)
        cv.put("isGuessed", 0)
        cv.put("guessedRank", 0)
        cv.put("example", example)
        cv.put("firstLangId", 1)
        cv.put("secondLangId", 2)
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

    @JvmStatic
    fun updateWordGuessedAndRankStatus(isRight : Boolean, wordId : Int, wordRank : Int) {
        val db = DBUtils.getDataBase().writableDatabase
        val values = ContentValues()
        values.put("isGuessed", 1)
        if (isRight) {
            values.put("guessedRank", wordRank + 1)
        } else {
            values.put("guessedRank", wordRank - 1)
        }
        val result = db.update("Words", values, "id = ?", arrayOf(wordId.toString()))
        db.close()
    }

    fun deleteWordsByDictionaryId(dictId: Int) {
        val db = DBUtils.getDataBase().writableDatabase
        db.delete("Words", "dictId = ?", arrayOf(dictId.toString()))
        db.close()
    }
}