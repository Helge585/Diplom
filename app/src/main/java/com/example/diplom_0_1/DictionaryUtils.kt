package com.example.diplom_0_1

import android.widget.LinearLayout

object DictionaryUtils {
    enum class Mode {
        Edit, WritingFirstTest, WritingSecondTest, ChoosingTest
    }
    class TestMode(val name : String, val mode : Mode) {}

    @JvmStatic
    fun getTestModesList() : List<TestMode> {
        val result = mutableListOf<TestMode>()
        result.add(TestMode("Ввод на родном языке", Mode.WritingFirstTest))
        result.add(TestMode("Ввод на иностранном языке", Mode.WritingSecondTest))
        result.add(TestMode("Выбрать слово", Mode.ChoosingTest))
        return result
    }

    @JvmStatic
    private var currentDictionaryName = "Default"
    @JvmStatic
    private var currentMode = Mode.Edit

    @JvmStatic
    fun setCurrentDictionaryName(dictName : String) {
        currentDictionaryName = dictName
    }

    @JvmStatic
    fun getCurrentDictionaryName() : String {
        return currentDictionaryName
    }

    @JvmStatic
    fun setCurrentMode(mode : Mode) {
        currentMode = mode
    }

    @JvmStatic
    fun getCurrentMode() : Mode {
        return currentMode
    }
}