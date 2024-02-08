package com.example.diplom_0_1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.diplom_0_1.dictionary.Dictionary
import com.example.diplom_0_1.dictionary.DictionaryAnnotationView
import com.example.diplom_0_1.test.TestUtils
import com.example.diplom_0_1.MainActivity
import com.example.diplom_0_1.R
import com.example.diplom_0_1.db.DictionaryDAO
import com.example.diplom_0_1.db.WordDAO
import com.example.diplom_0_1.dialogfragments.CreateDictionaryDialogFragment
import com.example.diplom_0_1.dialogfragments.ShowDictionaryInformationDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DictionariesFragment : Fragment(), View.OnClickListener {
    private lateinit var linearLayout : LinearLayout
    private var dicts = mutableListOf<Dictionary>()
    private val createDictionaryDialogFragment = CreateDictionaryDialogFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dictionaries, container, false)

        linearLayout = view.findViewById(R.id.linearLayoutDicts)

        DictionaryDAO.getAllDictionaries().forEach {
            Log.i("Dictionary", it.toString())
            dicts.add(it)
            val dw = DictionaryAnnotationView(id, it, context)
            dw.setOnClickListener {
                val w = WordDAO.getAllWords()
                for (word in w) {
                    Log.i("Dictionaries fragment, Info", word.toString())
                }
                val menuDialogFragment = ShowDictionaryInformationDialogFragment(this, dw.dictionary)
                menuDialogFragment.arguments = dw.dictionary.getBundleInfo()
                menuDialogFragment.show(activity?.supportFragmentManager!!, "dictMenu")
            }
            linearLayout.addView(dw)

        }

        val fab = view.findViewById<FloatingActionButton>(R.id.fabDict)
        fab.setOnClickListener {
            createDictionaryDialogFragment.show(activity!!.supportFragmentManager, "createDict")
        }
        (activity as MainActivity).setOnDictionariesFragment(this)
        return view
    }

    fun openDictionary(dict : Dictionary, mode : TestUtils.Mode) {
        TestUtils.currentDictionaryId = dict.id
        TestUtils.currentMode = mode
        findNavController().navigate(R.id.action_dictionariesFragment_to_dictionaryEditingFragment)
    }
    fun getDictionariesNames() : List<String> {

        val names = mutableListOf<String>()
        dicts.forEach { names.add(it.name) }
        return names
    }
    override fun onClick(p0: View?) {
        Log.i("DictionariesFragmnet", getDictionariesNames().toString())
    }
}