package com.example.diplom_0_1.test

import android.util.Log
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
        Log.i("TestsFactory", "allWords size = " + allWords.size)
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
        return when (testWordType) {
            TestWordType.Any -> {
                getAnyTests(wordsCount, testDirectionType)
            }
            TestWordType.Wrong -> {
                getWrongTests(wordsCount, testDirectionType)
            }
            TestWordType.New -> {
                getNewTests(wordsCount, testDirectionType)
            }
        }
    }

    @JvmStatic
    fun getWrongAnswersForWord(word: Word, isFirstWords: Boolean) : List<String> {
        val randomWords = mutableListOf<String>()
        var wordCount = 5
        if (allWords.size <= 5) {
            wordCount = allWords.size - 1
        }
        var showedWord = ""
        if (isFirstWords) {
            randomWords.add(word.firstWord)
            showedWord = word.secondWord
        } else {
            randomWords.add(word.secondWord)
            showedWord = word.firstWord
        }
        var i = 0;
        while (i < wordCount) {
            val randomPair = allWords.random()
            val randomWord = if (isFirstWords) { randomPair.firstWord } else { randomPair.secondWord }
            if (randomWord != showedWord && !randomWords.contains(randomWord)) {
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
                allWords.shuffled().subList(0, _wordsCount)
            }
            TestDirectionType.Begin -> {
                allWords.subList(0, _wordsCount)
            }
            TestDirectionType.End -> {
                allWords.subList(allWords.size - _wordsCount, allWords.size)
            }
        }
    }

    private fun getNewTests(wordsCount : Int, testDirectionType: TestDirectionType) : List<Word> {
        val _wordsCount : Int
        if (wordsCount == -1) {
            _wordsCount = newWords.size
        } else {
            _wordsCount = wordsCount
        }
        return when(testDirectionType) {
            TestDirectionType.Random -> {
                newWords.shuffled().subList(0, _wordsCount)
            }
            TestDirectionType.Begin -> {
                newWords.subList(0, _wordsCount)
            }
            TestDirectionType.End -> {
                newWords.subList(allWords.size - _wordsCount, allWords.size)
            }
        }
    }

    private fun getWrongTests(wordsCount : Int, testDirectionType: TestDirectionType) : List<Word> {
        val _wordsCount : Int
        if (wordsCount == -1) {
            _wordsCount = wrongWords.size
        } else {
            _wordsCount = wordsCount
        }
        return when(testDirectionType) {
            TestDirectionType.Random -> {
                wrongWords.shuffled().subList(0, _wordsCount)
            }
            TestDirectionType.Begin -> {
                wrongWords.subList(0, _wordsCount)
            }
            TestDirectionType.End -> {
                wrongWords.subList(allWords.size - _wordsCount, allWords.size)
            }
        }
    }
}