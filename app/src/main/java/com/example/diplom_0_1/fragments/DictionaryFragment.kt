package com.example.diplom_0_1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.diplom_0_1.MainActivity
import com.example.diplom_0_1.R
import com.example.diplom_0_1.db.TestDAO
import com.example.diplom_0_1.db.WordDAO
import com.example.diplom_0_1.dialogfragments.SelectTestSettingsDialogFragment
import com.example.diplom_0_1.dialogfragments.ShowTestResultFragmentDialog
import com.example.diplom_0_1.test.TestCase
import com.example.diplom_0_1.test.TestUtils
import com.example.diplom_0_1.test.TestUtils.Mode
import com.example.diplom_0_1.test.TestsFactory
import com.example.diplom_0_1.test.WordChoosingTestView
import com.example.diplom_0_1.test.WordView
import com.example.diplom_0_1.test.WordWritingTestView


class DictionaryFragment : Fragment() {

    private lateinit var settingButton : Button
    private lateinit var finishButton: Button
    private lateinit var linearLayoutMain : LinearLayout
    private val selectTestSettingsDialogFragment = SelectTestSettingsDialogFragment()

    private var newWordsCount = 0;
    private var wrongGuessWordsCount = 0;
    private var allWordsCount = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).bottomNavView.isVisible = false
        val view = inflater.inflate(R.layout.fragment_dictionary_editing, container, false)
        val dictId = TestUtils.currentDictionaryId
        TestsFactory.initTestsFactory(dictId)

        settingButton = view.findViewById(R.id.buttonSettings)
        settingButton.setText("Настроить")
        settingButton.setOnClickListener {
            val args = Bundle()
            args.putInt("allWordsCount", allWordsCount)
            args.putInt("newWordsCount", newWordsCount)
            args.putInt("wrongGuessWordsCount", wrongGuessWordsCount)
            selectTestSettingsDialogFragment.arguments = args
            selectTestSettingsDialogFragment.show(activity!!.supportFragmentManager, "testSettings")
        }
        finishButton = view.findViewById(R.id.buttonFinish)
        finishButton.setText("Завершить")
        finishButton.setOnClickListener {
            var triedCount = 0
            var rightCount = 0
            var negatinveRankCount = 0
            val mode = TestUtils.currentMode

            linearLayoutMain.forEach {
                if (it is TestCase) {
                    if (it.isTried()) {
                        it.word.isGuessed = 1
                        ++triedCount
                        if (it.isRight()) {
                            ++rightCount
                        } else {
                            --it.word.guessedRank
                            if (it.word.guessedRank < 0) {
                                ++negatinveRankCount
                            }
                        }
                        WordDAO.updateWordGuessedAndRankStatus(it.isRight(), it.word.id, it.word.guessedRank)
                    }
                }
            }
            wrongGuessWordsCount = negatinveRankCount
            newWordsCount = allWordsCount - triedCount
            TestDAO.updateTest(dictId, rightCount.toDouble() / triedCount, mode)
            Log.i("Dictionary Fragment", "mode = $mode, triedCount = $triedCount, rightCount = $rightCount, allCount = $allWordsCount")
            val resultShow = ShowTestResultFragmentDialog()
            val args = Bundle()
            args.putString("result", "\nВсего слов: $allWordsCount\nОтвечены: $triedCount\nВерно: $rightCount\n" +
                    "Неверно: ${triedCount - rightCount}")
            resultShow.arguments = args
            resultShow.show(activity?.supportFragmentManager!!, "testResult")
        }

        linearLayoutMain = view.findViewById(R.id.linnn)

        val words = TestsFactory.getTestsList(TestUtils.TestWordType.Any, TestUtils.TestDirectionType.Random, -1)

        allWordsCount = words.size
        words.forEach {
            if (it.isGuessed == 0) {
                ++newWordsCount
            }
            if (it.guessedRank < 0) {
                ++wrongGuessWordsCount
            }
        }
        updateTestsList(TestUtils.TestWordType.Any, TestUtils.TestDirectionType.Random, -1)
        (activity as MainActivity).setOnDictionaryEditingFragment(this)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).bottomNavView.isVisible = true
        Log.i("Dictionary Fragment", "onDestroyView")
    }

    fun updateTestsList(testsWordsType: TestUtils.TestWordType,
                        testDirectionType: TestUtils.TestDirectionType, wordsCount: Int) {
        linearLayoutMain.removeAllViews()
        val words = TestsFactory.getTestsList(testsWordsType, testDirectionType, wordsCount)
        val mode = TestUtils.currentMode
        when (mode) {
            Mode.Edit -> {
                words.forEach {
                    linearLayoutMain.addView(WordView(it, context))
                }
            }
            Mode.WritingFirstTest -> {
                words.forEach { linearLayoutMain.addView(WordWritingTestView(it, context, Mode.WritingFirstTest)) }
            }
            Mode.WritingSecondTest -> {
                words.forEach { linearLayoutMain.addView(WordWritingTestView(it, context, Mode.WritingSecondTest)) }
            }
            Mode.ChoosingFirstTest -> {
                words.forEach {
                    linearLayoutMain.addView(WordChoosingTestView(it,
                        TestsFactory.getWrongAnswersForWord(it, true),
                        context, Mode.ChoosingFirstTest))
                }
            }
            Mode.ChoosingSecondTest -> {
                words.forEach {
                    linearLayoutMain.addView(WordChoosingTestView(it,
                        TestsFactory.getWrongAnswersForWord(it, false),
                        context, Mode.ChoosingSecondTest))
                }
            }
        }
    }
}