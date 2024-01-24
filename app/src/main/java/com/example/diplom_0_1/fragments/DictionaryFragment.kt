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
import com.example.diplom_0_1.dialogfragments.ShowTestResultFragmentDialog
import com.example.diplom_0_1.test.TestCase
import com.example.diplom_0_1.test.TestUtils
import com.example.diplom_0_1.test.TestUtils.Mode
import com.example.diplom_0_1.test.TestsFactory
import com.example.diplom_0_1.test.WordChoosingTestView
import com.example.diplom_0_1.test.WordView
import com.example.diplom_0_1.test.WordWritingTestView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DictionaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DictionaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var settingButton : Button
    private lateinit var finishButton: Button
    private lateinit var linearLayoutMain : LinearLayout


    private var newWordsCount = 0;
    private var wrongGuessWordsCount = 0;
    private var allWordsCount = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity).bottomNavView.isVisible = false
        val view = inflater.inflate(R.layout.fragment_dictionary_editing, container, false)
        val dictId = TestUtils.currentDictionaryId
        TestsFactory.initTestsFactory(dictId)

        settingButton = view.findViewById(R.id.buttonSettings)
        settingButton.setText("Настроить")
        settingButton.setOnClickListener {
            (activity as MainActivity).showTestSettingsDialogFragment(allWordsCount, newWordsCount,
                wrongGuessWordsCount)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DictionaryEditingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DictionaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}