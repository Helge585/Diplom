package com.example.diplom_0_1

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DictionariesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dictionaries, container, false)

        linearLayout = view.findViewById(R.id.linearLayoutDicts)

        val db = (activity as MainActivity).dbManager.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Dictionaries", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val dict = Dictionary(id, name)
            dicts.add(dict)
            val dw = DictionaryView(id, name, context)
            dw.setOnClickListener(this)
            dw.setOnClickListener {
                //view.findNavController().navigate(R.id.action_booksFragment_to_readingFragment)
            }
            linearLayout.addView(dw)
        }
        cursor.close()
        db.close()

        (activity as MainActivity).setOnDictionariesFragment(this)
        return view
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