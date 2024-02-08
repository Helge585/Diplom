package com.example.diplom_0_1.db

import android.content.ContentValues
import com.example.diplom_0_1.dictionary.Dictionary
import com.example.diplom_0_1.test.TestResult

object DictionaryDAO {

    fun create(bookId : Int, name : String) {
        val db = DBUtils.getDataBase().writableDatabase
        val cv = ContentValues()
        cv.put("bookId", bookId)
        cv.put("name", name)
        db.insert("Dictionaries", null, cv)
        db.close()
        val dictId = getDictionaryIdByName(name)
        TestDAO.create(dictId)
    }
    @JvmStatic
    fun deleteById(dictId : Int) {
        WordDAO.deleteWordsByDictionaryId(dictId)
        TestDAO.deleteByDictId(dictId)
        val db = DBUtils.getDataBase().writableDatabase
        db.delete("Dictionaries", "id = ?", arrayOf(dictId.toString()))
        db.close()
    }

    @JvmStatic
    fun getAllDictionaries() : List<Dictionary> {
        val dicts = mutableListOf<Dictionary>()
        val db = DBUtils.getDataBase().readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Dictionaries", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val bookId = cursor.getInt(1)
            val name = cursor.getString(2)
            val cursor4 = db.rawQuery("SELECT name FROM Books WHERE id = $bookId", null)
            var booksName = "not book"
            if (cursor4.moveToNext()) {
                booksName = cursor4.getString(0)
            }
            cursor4.close()
            val dict = Dictionary(id, bookId, name, booksName)
            val cursor2 = db.rawQuery("SELECT id, testTypeId, lastResult, averageResult, lastDate " +
                    "FROM Tests WHERE dictId = $id", null)
            while (cursor2.moveToNext()) {
                val testId = cursor2.getInt(0)
                val testTypeId = cursor2.getInt(1)
                val lastResult = cursor2.getDouble(2)
                val averageResult = cursor2.getDouble(3)
                val lastDate = cursor2.getString(4)
                val cursor3 = db.rawQuery("SELECT name FROM TestTypes WHERE id = $testTypeId", null)
                var testTypeIdString = "not type"
                if (cursor3.moveToNext()) {
                    testTypeIdString = cursor3.getString(0)
                }
                cursor3.close()
                val testResult = TestResult(testId, id, testTypeIdString, lastResult, averageResult, lastDate)
                dict.addTestResult(testResult)
            }
            cursor2.close()
            dicts.add(dict)
        }
        cursor.close()
        db.close()
        return dicts
    }

    @JvmStatic
    fun getDictionaryIdByName(nname : String) : Int {
        val db = DBUtils.getDataBase().readableDatabase
        val cursor = db.rawQuery("SELECT id FROM Dictionaries WHERE name = '$nname'", null)
        var result = 0
        if (cursor.moveToNext()) {
            result = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return result
    }
}