package com.example.diplom_0_1.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.example.diplom_0_1.MainActivity
import com.example.diplom_0_1.translate.Translating

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChooseWordDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseWordDialogFragment(
    val translatings: List<Translating>,
    val word: String,
    val currentSentence: String
) : DialogFragment() {
    private var checkedItemIndex = 0
    private var checkedWord = ""
    private var checkedSentence = ""
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val mainLayout = LinearLayout(context)
        mainLayout.orientation = VERTICAL
        val trRadioGroup = RadioGroup(context)
        val exRadioGroup = RadioGroup(context)
        val exTitle = TextView(context)
        exTitle.setText("Примеры")
        exTitle.gravity = Gravity.CENTER
        exRadioGroup.addView(exTitle)
        var isFirst = true
        translatings.forEach {
            val posText = TextView(context)
            posText.setText(getPosRus(it.partOfSpeech))
            trRadioGroup.addView(posText)
            it.translatings.forEach {
                val rb = RadioButton(context)
                rb.setText(it)
                trRadioGroup.addView(rb)
                if (isFirst) {
                    trRadioGroup.check(rb.id)
                    isFirst = false
                }
            }
            it.examples.forEach {
                val rbex = RadioButton(context)
                rbex.setText(it)
                exRadioGroup.addView(rbex)
            }
        }

        val rbex = RadioButton(context)
        rbex.setText(currentSentence)
        exRadioGroup.addView(rbex)
        exRadioGroup.check(rbex.id)

        trRadioGroup.setPadding(50, 50, 30, 30)
        exRadioGroup.setPadding(50, 50, 30, 30)

//        exRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
//            val id = radioGroup.checkedRadioButtonId
//            checkedSentence = (radioGroup.get(id) as RadioButton).text.toString()
//        }
//
//        trRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
//            val id = radioGroup.checkedRadioButtonId
//            checkedWord = (radioGroup.get(id) as RadioButton).text.toString()
//        }
        mainLayout.addView(trRadioGroup)
        mainLayout.addView(exRadioGroup)

        val scrollView = ScrollView(context)
        scrollView.addView(mainLayout)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(word)
                .setView(scrollView)
                .setPositiveButton(
                    "Далее"
                ) { dialog, id ->
                    trRadioGroup.children.forEach {
                        if (it is RadioButton) {
                            if (it.isChecked) {
                                checkedWord = it.text.toString()
                            }
                        }
                    }
                    exRadioGroup.children.forEach {
                        if (it is RadioButton) {
                            if (it.isChecked) {
                                checkedSentence = it.text.toString()
                            }
                        }
                    }
                    Log.i("ChooseWordFragment", "checked word = $checkedWord, checked sentence = $checkedSentence")
                    (activity as MainActivity).showDictionariesChoosenDialogFragment(word, checkedWord, checkedSentence)
                }
                .setNegativeButton("Отмена") { dialog, id ->
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun getPosRus(posEng : String) : String{
        return when(posEng) {
            "noun" -> {
                "существительное"
            }
            "adjective" -> {
                "прилагательное"
            }
            "pronoun" -> {
                "местоимение"
            }
            "verb" -> {
                "глагол"
            }
            "adverb" -> {
                "наречие"
            }
            "preposition" -> {
                "предлог"
            }
            "conjunction" -> {
                "союз"
            }
            "interjection" -> {
                "междометия"
            }
            else -> {
                posEng
            }
        }
    }
}

//package com.example.diplom_0_1.dialogfragments
//
//import android.app.Dialog
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import androidx.appcompat.app.AlertDialog
//import androidx.fragment.app.DialogFragment
//import com.example.diplom_0_1.MainActivity
//import com.example.diplom_0_1.translate.Translating
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// * Use the [ChooseWordDialogFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//class ChooseWordDialogFragment(val currentTranslatings: List<Translating>) : DialogFragment() {
//    private lateinit var translatings : Array<String>
//    private lateinit var word : String
//    private var checkedItemIndex = 0
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//
//        word = arguments?.getString("word") ?: ""
//        translatings = arguments?.getStringArray("translatings") as Array<String>
//
//        checkedItemIndex = 0
//
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            builder.setTitle(word)
//                .setSingleChoiceItems(
//                    translatings, checkedItemIndex
//                ) { dialog, item ->
//                    checkedItemIndex = item
//                    //Log.i("ChooseWordFragment", "Choosen item: " + translatings[item])
//                }
//                .setPositiveButton(
//                    "Далее"
//                ) { dialog, id ->
//                    Log.i("ChooseWordFragment", "Choosen item: " + translatings[checkedItemIndex])
//                    (activity as MainActivity).showDictionariesChoosenDialogFragment(word, translatings[checkedItemIndex])
//                }
//                .setNegativeButton("Отмена") { dialog, id ->
//                }
//
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }
//}
