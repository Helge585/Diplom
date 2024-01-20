package com.example.diplom_0_1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.diplom_0_1.dictionary.Dictionary
import com.example.diplom_0_1.dictionary.DictionaryAnnotationView
import com.example.diplom_0_1.test.TestUtils
import com.example.diplom_0_1.MainActivity
import com.example.diplom_0_1.R
import com.example.diplom_0_1.db.DictionaryDAO
import com.example.diplom_0_1.db.WordDAO
import com.example.diplom_0_1.dialogfragments.ChooseTestSettingsDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * Use the [DictionariesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DictionariesFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var linearLayout : LinearLayout
    private var dicts = mutableListOf<Dictionary>()

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
        val view = inflater.inflate(R.layout.fragment_dictionaries, container, false)

        linearLayout = view.findViewById(R.id.linearLayoutDicts)

        DictionaryDAO.getAllDictionaries().forEach {
            Log.i("Dictionary", it.toString())
            dicts.add(it)
            val dw = DictionaryAnnotationView(id, it, context)
            dw.getOpenButton().setOnClickListener {
                openDictionary(dw.dictionary, TestUtils.Mode.Edit)
            }
            dw.getTestButton().setOnClickListener {
                TestUtils.currentDictionaryId = dw.dictionary.id
                (activity as MainActivity).showTestChoosingFragmentDialog(dw.dictionary)
            }
            dw.getInfoButton().setOnClickListener {
                val w = WordDAO.getAllWords()
                for (word in w) {
                    Log.i("Dictionaries fragment, Info", word.toString())
                }
                (activity as MainActivity).showDictionaryInformationDialogFragment(dw.dictionary.getBundleInfo())

            }
            linearLayout.addView(dw)

        }

        val fab = view.findViewById<FloatingActionButton>(R.id.fabDict)
        fab.setOnClickListener {
            (activity as MainActivity).showCreateDictionaryDialogFragment()
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DictionariesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DictionariesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(p0: View?) {
        Log.i("DictionariesFragmnet", getDictionariesNames().toString())
    }
}