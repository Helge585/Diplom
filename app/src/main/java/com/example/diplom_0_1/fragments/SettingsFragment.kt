package com.example.diplom_0_1.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.children
import com.example.diplom_0_1.R
import com.example.diplom_0_1.setting.SettingsUtils
import com.example.diplom_0_1.test.TestUtils

class SettingsFragment : Fragment(), SeekBar.OnSeekBarChangeListener {
    private lateinit var twSizeExample : TextView
    private lateinit var sbLetterSize : SeekBar
    private lateinit var ltblColors : TableLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        ltblColors = view.findViewById(R.id.colorsTable)

        ltblColors.children.forEach {
            (it as TableRow).children.forEach {
                (it as Button).setOnClickListener {
                    Log.i("SettingsUtils", "id = " + (it as Button).id + ", name = " + it.text)
                    SettingsUtils.setTheme(it.text.toString())
                    activity?.recreate()
                }
            }
        }
//        for (i in 0..2) {
//            val traw = TableRow(context)
//            for (j in 0..3) {
//                val but = Button(context)
//                traw.addView(but)
//            }
//            ltblColors.addView(traw)
//        }
////        val but1 = view.findViewById<Button>(R.id.buttonSettings1)
//        but1.setOnClickListener {
//            TestUtils.themeMode = 0
//            activity?.recreate()
//        }
//        val but2 = view.findViewById<Button>(R.id.buttonSettings2)
//        but2.setOnClickListener {
//            TestUtils.themeMode = 1
//            activity?.recreate()
//        }

        twSizeExample = view.findViewById<TextView>(R.id.textSizeExample)
        twSizeExample.textSize = SettingsUtils.getLetterSizeSp()

        sbLetterSize = view.findViewById<SeekBar>(R.id.seekBar3)
        sbLetterSize.min = SettingsUtils.minLetterSizePx
        sbLetterSize.max = SettingsUtils.maxLetterSizePx
        sbLetterSize.setOnSeekBarChangeListener(this)
        sbLetterSize.setProgress(SettingsUtils.getLetterSizePx().toInt())
        return view
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        SettingsUtils.setLetterSizePx(sbLetterSize.progress.toFloat())
        twSizeExample.textSize = SettingsUtils.getLetterSizeSp()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        return
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        SettingsUtils.setLetterSizePx(sbLetterSize.progress.toFloat())
        twSizeExample.textSize = SettingsUtils.getLetterSizeSp()
    }

    fun onClick(view: View) {
        Log.i("SettingsUtils", "id = " + (view as Button).id)
    }
}