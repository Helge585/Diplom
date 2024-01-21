package com.example.diplom_0_1.test

import java.util.Date

//db?.execSQL("CREATE TABLE Tests(id INTEGER PRIMARY KEY AUTOINCREMENT, dictId INTEGER," +
//"testType testTypeId, lastResult REAL, averageResult REAL, lastDate REAL)")

class TestResult (val id : Int, val dictId : Int, val testType : String, val lastResult : Double,
                  val averageResult : Double, val lastDate: String){

    fun getInfo() : String {
        val name = TestUtils.getTestTypeUserInterfaceName(testType)
        return "\nТип теста: $name\nПоследний результат: ${lastResult * 100}%\nСредний результат: ${averageResult * 100}\nПоследний раз: $lastDate"
    }
}