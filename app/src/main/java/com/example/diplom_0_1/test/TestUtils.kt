package com.example.diplom_0_1.test

object TestUtils {
    enum class Mode {
        Edit, WritingFirstTest, WritingSecondTest, ChoosingFirstTest, ChoosingSecondTest
    }
    enum class TestDirectionType {
        Random, Begin, End
    }
    enum class TestWordType {
        Any, New, Wrong
    }
    class TestMode(val name : String, val mode : Mode) {}

    @JvmStatic
    var themeMode = 0
    @JvmStatic
    fun getTestModesList() : List<TestMode> {
        val result = mutableListOf<TestMode>()
        result.add(TestMode("Ввод на иностранном языке", Mode.WritingFirstTest))
        result.add(TestMode("Ввод на родном языке", Mode.WritingSecondTest))
        result.add(TestMode("Выбрать слово на инностранном языке", Mode.ChoosingFirstTest))
        result.add(TestMode("Выбрать слово на родном языке", Mode.ChoosingSecondTest))
        return result
    }

    @JvmStatic
    fun getTestTypeUserInterfaceName(innerName : String) : String{
        return when (innerName) {
            "WritingFirstTest" -> {
                "Ввод на иностранном языке"
            }
            "WritingSecondTest" -> {
                "Ввод на родном языке"
            }
            "ChoosingFirstTest" -> {
                "Выбрать слово на родном языке"
            }
            "ChoosingSecondTest" -> {
                "Выбрать слово на инностранном языке"
            }
            else -> {
                "Nema"
            }
        }
    }

    @JvmStatic
    var currentDictionaryName = "Default"

    @JvmStatic
    var currentMode = Mode.Edit

    @JvmStatic
    var currentDictionaryId = 1

}