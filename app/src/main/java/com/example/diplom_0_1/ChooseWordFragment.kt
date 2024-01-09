package com.example.diplom_0_1

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChooseWordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseWordFragment : DialogFragment() {
    private lateinit var translatings : Array<String>
    private lateinit var word : String
    private var checkedItemIndex = 0
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        word = arguments?.getString("word") ?: ""
        translatings = arguments?.getStringArray("translatings") as Array<String>

        checkedItemIndex = 0

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(word)
                .setSingleChoiceItems(
                    translatings, checkedItemIndex
                ) { dialog, item ->
                    checkedItemIndex = item
                    //Log.i("ChooseWordFragment", "Choosen item: " + translatings[item])
                }
                .setPositiveButton(
                    "Далее"
                ) { dialog, id ->
                    Log.i("ChooseWordFragment", "Choosen item: " + translatings[checkedItemIndex])
                    (activity as MainActivity).showDictionariesChoosenDialogFragment(word, translatings[checkedItemIndex])
                }
                .setNegativeButton("Отмена") { dialog, id ->
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}