package com.example.diplom_0_1.test

import android.text.Selection
import com.example.diplom_0_1.db.WordDAO
import com.example.diplom_0_1.test.TestUtils.TestDirectionType
import com.example.diplom_0_1.test.TestUtils.TestWordType


object TestsFactory {
    @JvmStatic
    private val allWords = mutableListOf<Word>()
    private val newWords = mutableListOf<Word>()
    private val wrongWords = mutableListOf<Word>()

    @JvmStatic
    fun initTestsFactory(dictId : Int) {
        allWords.clear()
        newWords.clear()
        wrongWords.clear()
        WordDAO.getAllWordsByDictionaryId(dictId).forEach { allWords.add(it) }
        allWords.forEach {
            if (it.isGuessed == 0) {
                newWords.add(it)
            }
            if (it.guessedRank < 0) {
                wrongWords.add(it)
            }
        }
        allWords.sortBy { it.id }
        newWords.sortBy { it.id }
        wrongWords.sortBy { it.id }
    }

    @JvmStatic
    fun getTestsList(testWordType : TestWordType, testDirectionType : TestDirectionType,
                  wordsCount : Int) : List<Word> {
        return getAnyTests(wordsCount, testDirectionType)
    }

    @JvmStatic
    fun getWrongAnswersForWord(rightWord : String, isFirstWords : Boolean) : List<String> {
        val randomWords = mutableListOf<String>()
        var wordCount = 5
        if (allWords.size <= 5) {
            wordCount = allWords.size - 1
        }
        var i = 0;
        while (i < wordCount) {
            val randomPair = allWords.random()
            val randomWord = if (isFirstWords) { randomPair.firstWord } else { randomPair.secondWord }
            if (randomWord != rightWord && !randomWords.contains(randomWord)) {
                randomWords.add(randomWord)
                ++i
            }
        }
        return randomWords.toList()
    }


    @JvmStatic
    private fun getAnyTests(wordsCount : Int, testDirectionType: TestDirectionType) : List<Word> {
        var _wordsCount : Int
        if (wordsCount == -1) {
            _wordsCount = allWords.size
        } else {
            _wordsCount = wordsCount
        }
        return when (testDirectionType) {
            TestDirectionType.Random -> {
                allWords.shuffled().subList(0, _wordsCount - 1)
            }
            TestDirectionType.Begin -> {
                allWords.subList(0, _wordsCount - 1)
            }
            TestDirectionType.End -> {
                allWords.subList(allWords.size - _wordsCount, allWords.size - 1)
            }
        }
    }

//    private fun getNewTests(wordsCount : Int, testDirectionType: TestDirectionType) : List<Word> {
//
//    }
//
//    private fun getWrongTests(wordsCount : Int, testDirectionType: TestDirectionType) : List<Word> {
//
//    }
}