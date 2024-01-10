package com.example.diplom_0_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import com.example.diplom_0_1.DictionaryUtils.Mode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DictionaryEditingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DictionaryEditingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_dictionary_editing, container, false)
        val linearLayout = view.findViewById<LinearLayout>(R.id.linnn)
        val dictName = DictionaryUtils.getCurrentDictionaryName()
        val dictId = DictionaryDAO.getDictionaryByName(dictName).id
        val words = WordDAO.getAllWordsByDictionaryId(dictId)
        val mode = DictionaryUtils.getCurrentMode()
        val secondWords = mutableListOf<String>()
        words.forEach { secondWords.add(it.secondWord) }
        when (mode) {
            Mode.Edit -> {
                words.forEach { linearLayout.addView(WordView(it, context)) }
            }
            Mode.WritingFirstTest -> {
                words.forEach { linearLayout.addView(WordTestView(it, context)) }
            }
            Mode.WritingSecondTest -> {
                words.forEach { linearLayout.addView(WordTestView(it, context, Mode.WritingSecondTest)) }
            }
            Mode.ChoosingTest -> {
                words.forEach {
                    linearLayout.addView(WordChoosingTestView(it, getRandomWords(it.firstWord, secondWords), context))
                }
            }
        }
        (activity as MainActivity).setOnDictionaryEditingFragment(this)
        return view
    }

    private fun getRandomWords(guessedWord : String, words : List<String>) : List<String> {
        val randomWords = mutableListOf<String>()
        var wordCount = 5
        if (words.size <= 5) {
            wordCount = words.size - 1
        }
        var i = 0;
        while (i < wordCount) {
            val randomWord = words.random()
            if (randomWord != guessedWord && !randomWords.contains(randomWord)) {
                randomWords.add(randomWord)
                ++i
            }
        }
        return randomWords.toList()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DictionaryEditingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DictionaryEditingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}