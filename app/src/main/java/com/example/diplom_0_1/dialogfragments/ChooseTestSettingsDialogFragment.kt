package com.example.diplom_0_1.dialogfragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.diplom_0_1.MainActivity
import com.example.diplom_0_1.test.TestUtils.TestWordType
import com.example.diplom_0_1.test.TestUtils.TestDirectionType


class ChooseTestSettingsDialogFragment : DialogFragment() {
    
    private var newWordsCount = 0;
    private var wrongGuessWordsCount = 0;
    private var allWordsCount = 0;
    private var currentWordsCount = 0;
    private var testsWordsType : TestWordType = TestWordType.Any
    private var testDirectionType : TestDirectionType = TestDirectionType.Random;

    private val testTypes = arrayOf("sscfs", "asdcsdv", "sdfsdf")
    private val testTypes2 = arrayOf("23234", "234", "23423423")
    //private var layout2 : LinearLayout? = null



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        Log.i("ChooseTestSettingsDialogFragment", "onCreateDialog")
        newWordsCount = arguments?.getInt("newWordsCount") ?: 0
        wrongGuessWordsCount = arguments?.getInt("wrongGuessWordsCount") ?: 0
        allWordsCount = arguments?.getInt("allWordsCount") ?: 0
        currentWordsCount = allWordsCount

        val wordCountTextView = TextView(context)
        val wordCountEditText = EditText(context)

        val radioGroupWordTypes = RadioGroup(context)
        val rbAllWord = RadioButton(context)
        rbAllWord.setText("Выбрать из всех: $allWordsCount")
        val rbNewWord = RadioButton(context)
        rbNewWord.setText("Выбрать из новых: $newWordsCount")
        val rbGuessWord = RadioButton(context)
        rbGuessWord.setText("Выбрать из неугаданных: $wrongGuessWordsCount")
        radioGroupWordTypes.addView(rbAllWord)
        radioGroupWordTypes.addView(rbNewWord)
        radioGroupWordTypes.addView(rbGuessWord)
        radioGroupWordTypes.setPadding(50, 30, 30, 30)
        radioGroupWordTypes.check(rbAllWord.id)
        radioGroupWordTypes.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                rbAllWord.id -> {
                    wordCountEditText.setText("" + allWordsCount)
                    currentWordsCount = allWordsCount
                    testsWordsType = TestWordType.Any
                }
                rbNewWord.id -> {
                    wordCountEditText.setText("" + newWordsCount)
                    currentWordsCount = newWordsCount
                    testsWordsType = TestWordType.New
                }
                rbGuessWord.id -> {
                    wordCountEditText.setText("" + wrongGuessWordsCount)
                    currentWordsCount = wrongGuessWordsCount
                    testsWordsType = TestWordType.Wrong
                }
            }
        }
        
        val layout = LinearLayout(context)
        layout.orientation = HORIZONTAL
        wordCountTextView.setText("Количество слов в тесте:")
        wordCountEditText.inputType = InputType.TYPE_CLASS_NUMBER
        wordCountEditText.layoutParams = ViewGroup.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        wordCountEditText.setText("" + currentWordsCount)
        wordCountEditText.setSingleLine(true)
        wordCountEditText.setOnKeyListener { view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_ENTER) {
                val inputCount = Integer.parseInt(wordCountEditText.text.toString())
                if (inputCount > currentWordsCount) {
                    wordCountEditText.setText("" + currentWordsCount)
                    wordCountEditText.setTextColor(Color.RED)
                } else {
                    wordCountEditText.setTextColor(Color.BLACK)
                }
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        layout.addView(wordCountTextView)
        layout.addView(wordCountEditText)
        layout.setPadding(50, 30, 30, 30)

        val radioGroupTestDirection = RadioGroup(context)
        val r1 = RadioButton(context)
        r1.setText("Случайная выборка")
        val r2 = RadioButton(context)
        r2.setText("Выборка из добавленных последними")
        val r3 = RadioButton(context)
        r3.setText("Выборка из добавленных первыми")
        radioGroupTestDirection.addView(r1)
        radioGroupTestDirection.addView(r2)
        radioGroupTestDirection.addView(r3)
        radioGroupTestDirection.check(r1.id)
        radioGroupTestDirection.setOnCheckedChangeListener { radioGroup, i ->
//            Log.i("ChooseTestSettingsDialogFragment", "i is " + i)
//            Log.i("ChooseTestSettingsDialogFragment", "checked buuton is " +
        //            (radioGroup.get(i - 4) as RadioButton).text)
            when (i) {
                r1.id -> {
                    testDirectionType = TestDirectionType.Random
                }
                r2.id -> {
                    testDirectionType = TestDirectionType.End
                }
                r3.id -> {
                    testDirectionType = TestDirectionType.Begin
                }
            }
        }
        radioGroupTestDirection.setPadding(50, 30, 30, 30)


        val layout2 = LinearLayout(context)
        layout2.orientation = VERTICAL
        layout2.addView(radioGroupWordTypes)
        layout2.addView(layout)
        layout2.addView(radioGroupTestDirection)



        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Test Settings")
//                .setSingleChoiceItems(
//                    testTypes, 1
//                ) { dialog, item ->
//
//                }
                .setView(layout2)
                .setPositiveButton("Выбрать") { dialog, id ->
                   // Log.i("ChooseTestSettingsDialogFragment", "$testsWordsType, $wt, $testDirectionType, $tt, " + wordCountEditText.text.toString())
                    (activity as MainActivity).updateDictionaryFragment(
                        testsWordsType, testDirectionType, Integer.parseInt(wordCountEditText.text.toString())
                    )
                }
                .setNegativeButton("Отменить") { dialog, id ->
                    //dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}