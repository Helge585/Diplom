package com.example.diplom_0_1.test

interface TestCase {
    val word : Word
    fun isTried() : Boolean
    fun isRight() : Boolean
    fun getTestType() : TestUtils.Mode
}