package com.example.diplom_0_1.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
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
    private var transalateStatus = false
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
        if (!hasConnection()) {
            transalateStatus = false
            translatedTextView.setTextColor(Color.RED)
            translatedTextView.setText("Нет соединения с интернетом")
            return
        }
        currentWord = word
        currentSentence = sentence
        Translator.translate(word)
        transalateStatus = Translator.getTranslateStatus()
        if ( transalateStatus) {
            translatedTextView.setTextColor(Color.BLACK)
            currentTranslatings = Translator.getTranslatings()
            currentTranslatings?.let {
                translatedText = currentTranslatings[0].translatings.toString()
                translatedTextView.setText(translatedText)
            }
        } else {
            translatedTextView.setTextColor(Color.RED)
            translatedTextView.setText("Произошла ошибка при переводе")
        }
    }
    override fun onClick(p0: View?) {
        if (transalateStatus) {
            val selectWordDialogFragment = SelectWordDialogFragment(currentTranslatings, currentWord, currentSentence)
            selectWordDialogFragment.show(activity?.supportFragmentManager!!, "choooseWord")
        }
    }
    fun hasConnection() : Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities != null &&
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }
}