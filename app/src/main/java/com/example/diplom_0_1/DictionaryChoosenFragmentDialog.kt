package com.example.diplom_0_1

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DictionaryChoosenFragmentDialog : DialogFragment() {

    private var firstWord = ""
    private var secondWord = ""
    private lateinit var dicts : Array<String>
    private var checkedItemIndex = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        firstWord = arguments?.getString("firstWord") ?: ""
        secondWord = arguments?.getString("secondWord") ?: ""
        checkedItemIndex = 0
        dicts = arguments?.getStringArray("dicts") as Array<String>

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(firstWord + " " + secondWord)
                .setSingleChoiceItems(
                    dicts, checkedItemIndex
                ) { dialog, item ->
                    checkedItemIndex = item
                    //Log.i("ChooseWordFragment", "Choosen item: " + translatings[item])
                }
                .setPositiveButton(
                    "Сохранить"
                ) { dialog, id ->
                    Log.i("DictionariesChoosenFragmentDialog", "Choosen item: " + dicts[checkedItemIndex])
                    WordDAO.saveWordByDictName(dicts[checkedItemIndex], firstWord, secondWord)
                }
                .setNegativeButton("Назад") { dialog, id ->
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}