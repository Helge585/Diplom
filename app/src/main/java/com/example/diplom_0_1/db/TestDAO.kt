package com.example.diplom_0_1.db

import android.content.ContentValues
import android.icu.util.Calendar
import android.util.Log
import com.example.diplom_0_1.test.TestUtils
import com.example.diplom_0_1.test.Word
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TestDAO {

    @JvmStatic
    fun deleteByDictId(dictId : Int) {
        val db = DBUtils.getDataBase().writableDatabase
        db.delete("Tests", "dictId = ?", arrayOf(dictId.toString()))
        db.close()
    }

    @JvmStatic
    fun create(dictId: Int) {
        val db = DBUtils.getDataBase().writableDatabase
        var cv = ContentValues()
        cv.put("dictId", dictId)
        cv.put("testTypeId", 1)
        cv.put("lastResult", 0)
        cv.put("averageResult", 0)
        cv.put("lastDate", "----")
        db.insert("Tests", null, cv)

        cv = ContentValues()
        cv.put("dictId", dictId)
        cv.put("testTypeId", 2)
        cv.put("lastResult", 0)
        cv.put("averageResult", 0)
        cv.put("lastDate", "----")
        db.insert("Tests", null, cv)

        cv = ContentValues()
        cv.put("dictId", dictId)
        cv.put("testTypeId", 3)
        cv.put("lastResult", 0)
        cv.put("averageResult", 0)
        cv.put("lastDate", "----")
        db.insert("Tests", null, cv)

        cv = ContentValues()
        cv.put("dictId", dictId)
        cv.put("testTypeId", 4)
        cv.put("lastResult", 0)
        cv.put("averageResult", 0)
        cv.put("lastDate", "----")
        db.insert("Tests", null, cv)
        db.close()
    }

    @JvmStatic
    fun updateTest(dictId : Int, lastResult : Double, mode : TestUtils.Mode) {
        val testTypeId = getTestTypeIdByName(mode.toString())
        var lastAR = getAverageResult(dictId, testTypeId)
        if (lastAR == 0.0) { lastAR = lastResult }

        val date = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val dateText: String = dateFormat.format(date)
        //Log.i("DictionaryFragment", "date = $dateText")

        val db = DBUtils.getDataBase().writableDatabase
        val values = ContentValues()
        values.put("lastResult", lastResult)
        values.put("averageResult", (lastAR + lastResult) / 2.0)
        values.put("lastDate", dateText)
        val result = db.update("Tests", values, "dictId = ? AND testTypeId = ?",
            arrayOf(dictId.toString(), testTypeId.toString()))
        db.close()
    }

    @JvmStatic
    fun getTestTypeIdByName(testTypeName : String) : Int {
        var testTypeId = 0
        val db = DBUtils.getDataBase().readableDatabase
        val cursor = db.rawQuery("SELECT id FROM TestTypes WHERE name = '$testTypeName'", null)
        if (cursor.moveToNext()) {
            testTypeId = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return testTypeId
    }

    @JvmStatic
    fun getAverageResult(dictId : Int, testTypeId : Int) : Double {
        var averageResult = 0.0
        val db = DBUtils.getDataBase().readableDatabase
        val cursor = db.rawQuery("SELECT averageResult FROM Tests WHERE dictId = $dictId AND testTypeId =$testTypeId", null)
        if (cursor.moveToNext()) {
            averageResult = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return averageResult
    }
}