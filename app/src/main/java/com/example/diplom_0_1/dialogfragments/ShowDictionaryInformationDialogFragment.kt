package com.example.diplom_0_1.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.diplom_0_1.MainActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChooseWordDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowDictionaryInformationDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val name = arguments?.getString("name") ?: ""
        val bookName = arguments?.getString("bookName") ?: ""
        val test0 = arguments?.getString("test0") ?: ""
        val test1 = arguments?.getString("test1") ?: ""
        val test2 = arguments?.getString("test2") ?: ""
        val test3 = arguments?.getString("test3") ?: ""
        val checkedItemIndex = 0

        val nameText = TextView(context)
        nameText.setText(name)
        val booText = TextView(context)
        booText.setText(bookName)
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
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Info")
                .setView(l)
                .setNegativeButton("Отмена") { dialog, id ->

                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}