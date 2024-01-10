package com.example.diplom_0_1

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController

class TestChoosingFragmentDialog : DialogFragment() {

    private var testsModes = listOf<DictionaryUtils.TestMode>()
    private var dictName = "Default"
    private var checkedItemIndex = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        testsModes = DictionaryUtils.getTestModesList()
        dictName = arguments?.getString("dictName") ?: "Default"
        val testsNames = mutableListOf<String>()
        testsModes.forEach { testsNames.add(it.name) }
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Тест для $dictName")
                .setSingleChoiceItems(
                    testsNames.toTypedArray(), checkedItemIndex
                ) { dialog, item ->
                    checkedItemIndex = item
                }
                .setPositiveButton("Выбрать") { dialog, id ->
                    DictionaryUtils.setCurrentMode(testsModes[checkedItemIndex].mode)
                    DictionaryUtils.setCurrentDictionaryName(testsModes[checkedItemIndex].name)
                    findNavController().navigate(R.id.action_dictionariesFragment_to_dictionaryEditingFragment)
                }
                .setNegativeButton("Отменить") { dialog, id ->
                    dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}