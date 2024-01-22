package com.example.diplom_0_1.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.diplom_0_1.test.TestUtils
import com.example.diplom_0_1.R

class ShowTestResultFragmentDialog : DialogFragment() {



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val textView = TextView(context)
        val result = arguments?.getString("result")
        textView.setText(result)
        textView.setPadding(50,30,50,30)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Результат")
                .setView(textView)
                .setPositiveButton("Ок") { dialog, id ->
                    findNavController().navigate(R.id.action_dictionaryEditingFragment_to_dictionariesFragment)
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}