package com.example.diplom_0_1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.diplom_0_1.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment(), View.OnTouchListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    //@SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        editText = view.findViewById<EditText>(R.id.settingsTextEdit)
        editText.setTextIsSelectable(false)
        //editText.setOnTouchListener(this)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    // Получаем координаты нажатия
                    val x = event.x.toInt()
                    val y = event.y.toInt()
                    // Получаем Layout для TextView
                    val layout = (v as TextView).layout
                    // Получаем позицию символа, на который нажал пользователь
                    val position = layout.getOffsetForHorizontal(layout.getLineForVertical(y), x.toFloat())
                    // Получаем начальную и конечную позиции слова
                    var start = position
                    var end = position
                    val text = layout.text.toString()
                    while (start > 0 && text[start - 1] != ' ') {
                        start--
                    }
                    while (end < text.length && text[end] != ' ') {
                        end++
                    }
                    // Выделяем слово
                    if (start != end) {
                        //textEdit.setSelection(start, end)
                        (v as EditText).setSelection(start, end)
                        println("!!!  " + start + " " + end)
                        println(v.toString())
                        return true
                    }
                }
            }
            return false
    }
}