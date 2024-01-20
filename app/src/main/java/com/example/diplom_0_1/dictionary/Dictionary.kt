package com.example.diplom_0_1.dictionary

import android.os.Bundle
import com.example.diplom_0_1.test.TestResult

class Dictionary(val id : Int, val bookId : Int, var name : String, val bookName : String) {
    var testResults : MutableList<TestResult>
    init {
        testResults = mutableListOf()
    }

    fun addTestResult(testResult: TestResult) {
        testResults.add(testResult)
    }

    override fun toString(): String {
        return "id = $id, bookId = $bookId, name = $name, bookName = $bookName, " +
                "testResults = " + testResults.toString()
    }

    fun getBundleInfo(): Bundle {
        val args = Bundle()
        args.putString("name", name)
        args.putString("bookName", bookName)
        testResults.forEachIndexed { index, testResult ->
            args.putString("test$index", testResult.getInfo())
        }
        return args
    }
}