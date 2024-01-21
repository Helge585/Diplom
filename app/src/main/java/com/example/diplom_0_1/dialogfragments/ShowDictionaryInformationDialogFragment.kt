package com.example.diplom_0_1.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.diplom_0_1.MainActivity
import com.example.diplom_0_1.db.DictionaryDAO
import com.example.diplom_0_1.dictionary.Dictionary
import com.example.diplom_0_1.fragments.DictionariesFragment
import com.example.diplom_0_1.test.TestUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChooseWordDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowDictionaryInformationDialogFragment(
    val dictsFragment: DictionariesFragment,
    val dictionary: Dictionary
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val name = arguments?.getString("name") ?: ""
        val bookName = arguments?.getString("bookName") ?: ""
        val test0 = arguments?.getString("test0") ?: ""
        val test1 = arguments?.getString("test1") ?: ""
        val test2 = arguments?.getString("test2") ?: ""
        val test3 = arguments?.getString("test3") ?: ""
        val checkedItemIndex = 0

        val nameText = TextView(context)
        nameText.setText("Словарь: $name")
        val booText = TextView(context)
        booText.setText("Книга: $bookName")
        val test0Text = TextView(context)
        test0Text.setText(test0)
        val test1Text = TextView(context)
        test1Text.setText(test1)
        val test2Text = TextView(context)
        test2Text.setText(test2)
        val test3Text = TextView(context)
        test3Text.setText(test3)
        val l = LinearLayout(context)
        l.orientation = VERTICAL
        l.addView(nameText)
        l.addView(booText)
        l.addView(test0Text)
        l.addView(test1Text)
        l.addView(test2Text)
        l.addView(test3Text)
        l.setPadding(50, 30, 50, 30)

        val btOpen = Button(context)
        btOpen.setText("Открыть")
        btOpen.setOnClickListener {
            dictsFragment.openDictionary(dictionary, TestUtils.Mode.Edit)
            dismiss()
        }

        val btTest = Button(context)
        btTest.setText("Тест")
        btTest.setOnClickListener {
            TestUtils.currentDictionaryId = dictionary.id
            (activity as MainActivity).showTestChoosingFragmentDialog(dictionary)
            dismiss()
        }
        val btDelete = Button(context)
        btDelete.setText("Удалить")
        btDelete.setOnClickListener {
            DictionaryDAO.deleteById(dictionary.id)
            dismiss()
        }
        val layoutButtons = LinearLayout(context)
        layoutButtons.orientation = HORIZONTAL
        layoutButtons.addView(btOpen)
        layoutButtons.addView(btTest)
        layoutButtons.addView(btDelete)
        layoutButtons.setPadding(50, 30, 50, 30)

        val mainLayout = LinearLayout(context)
        mainLayout.orientation = VERTICAL
        mainLayout.addView(layoutButtons)
        mainLayout.addView(l)

        val scrollView = ScrollView(context)
        scrollView.addView(mainLayout)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("")
                .setView(scrollView)
                .setNegativeButton("Отмена") { dialog, id ->

                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}