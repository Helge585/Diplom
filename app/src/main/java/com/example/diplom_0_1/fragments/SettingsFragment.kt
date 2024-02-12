package com.example.diplom_0_1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.diplom_0_1.R
import com.example.diplom_0_1.test.TestUtils

class SettingsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val but1 = view.findViewById<Button>(R.id.buttonSettings1)
        but1.setOnClickListener {
            TestUtils.themeMode = 0
            activity?.recreate()
        }
        val but2 = view.findViewById<Button>(R.id.buttonSettings2)
        but2.setOnClickListener {
            TestUtils.themeMode = 1
            activity?.recreate()
        }
        return view
    }
}