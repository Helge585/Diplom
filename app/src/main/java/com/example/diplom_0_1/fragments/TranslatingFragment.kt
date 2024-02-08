package com.example.diplom_0_1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.diplom_0_1.R
import com.example.diplom_0_1.dialogfragments.SelectWordDialogFragment
import com.example.diplom_0_1.translate.Translating
import com.example.diplom_0_1.translate.Translator

class TranslatingFragment : Fragment(), View.OnClickListener {
    lateinit var translatedTextView : TextView

    private var translatedText = ""
    private var currentWord = "Not choosen"
    private var currentSentence = ""
    private var currentTranslatings = listOf<Translating>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_translating, container, false)
        translatedTextView = view.findViewById(R.id.textViewTransalting)
        translatedTextView.setText(translatedText)
        translatedTextView.setOnClickListener(this)
        return view
    }

    fun updateTranslating(word: String, sentence: String) {
        currentWord = word
        currentSentence = sentence
        Translator.translate(word)
        currentTranslatings = Translator.getTranslatings()
        translatedText = currentTranslatings[0].translatings.toString()
        translatedTextView.setText(translatedText)
    }

    override fun onClick(p0: View?) {
        val selectWordDialogFragment = SelectWordDialogFragment(currentTranslatings, currentWord, currentSentence)
        selectWordDialogFragment.show(activity?.supportFragmentManager!!, "choooseWord")
    }
}