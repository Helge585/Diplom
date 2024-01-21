package com.example.diplom_0_1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.diplom_0_1.R
import com.example.diplom_0_1.dialogfragments.ChooseWordDialogFragment
import com.example.diplom_0_1.translate.ResponseParser
import com.example.diplom_0_1.translate.Translating
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.SAXParserFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TranslatingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TranslatingFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var translatedTextView : TextView

    private var translatedText = ""
    private var currentWord = "Not choosen"
    private var currentSentence = ""
    private var currentTranslatings = listOf<Translating>()

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
        val view = inflater.inflate(R.layout.fragment_translating, container, false)
        translatedTextView = view.findViewById(R.id.textViewTransalting)
        translatedTextView.setText(translatedText)
        translatedTextView.setOnClickListener(this)
        return view
    }

    fun OnRecievedTranslatedWordFromMainActivity(word: String, sentence: String) {
        currentWord = word
        currentSentence = sentence
        //textView.setText(word)
        translate(word)
        translatedTextView.setText(translatedText)
    }

    private fun parseResponse(response : String) {
        val factory = SAXParserFactory.newInstance()
        val saxParser = factory.newSAXParser()
        val responseParser = ResponseParser()
        saxParser.parse(response.byteInputStream(), responseParser)
//        context?.applicationContext?.contentResolver?.openInputStream()?.use { inputStream ->
//            saxParser.parse(inputStream, fB2Parser)
//        }
        currentTranslatings = responseParser.getTranslatings()
        translatedText = currentTranslatings[0].translatings.toString()
        Log.i("Parsed Response", translatedText)
        responseParser.getTranslatings().forEach {
            Log.i("TranslatingFragment", it.toString())
        }

    }

    private fun translate(word : String) {
        Thread {
            //val url = URL("https://dictionary.yandex.net/api/v1/dicservice/lookup")
            val url = URL("https://dictionary.yandex.net/api/v1/dicservice/lookup?key=dict.1.1.20231219T204207Z.9540b67d9cf706ca.ce8ca752ce01b3e75303d82a10f1404b8e4fab94&lang=en-ru&text=$word&flags=4")

            val con = url.openConnection() as HttpURLConnection
            con.setRequestMethod("GET")
//                con.setRequestProperty("key", "dict.1.1.20231219T204207Z.9540b67d9cf706ca.ce8ca752ce01b3e75303d82a10f1404b8e4fab94")
//                con.setRequestProperty("lang", "en-ru")
//                con.setRequestProperty("text", selectedText.toString())

            val responseCode = con.responseCode
            println("GET Response Code :: $responseCode")
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val iinn = BufferedReader(InputStreamReader(con.inputStream))
                var inputLine: String?
                val response = StringBuffer()
                while (iinn.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                iinn.close()


                println(response.toString())
                //translatedWord = response.toString()
                Log.i("Response", response.toString())
                parseResponse(response.toString())
            } else {

            }
        }.start()
    }
    override fun onClick(p0: View?) {
        val chooseWordDialogFragment = ChooseWordDialogFragment(currentTranslatings, currentWord, currentSentence)
        chooseWordDialogFragment.show(activity?.supportFragmentManager!!, "choooseWord")
        //(activity as MainActivity).showChooseWordFragment(currentWord, currentTranslatings)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TranslatingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TranslatingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}