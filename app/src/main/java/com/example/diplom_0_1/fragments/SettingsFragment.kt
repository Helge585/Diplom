package com.example.diplom_0_1.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.diplom_0_1.R
import com.example.diplom_0_1.setting.SettingsUtils

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
                    SettingsUtils.setTheme((it as Button).text.toString())
                    activity?.recreate()
                }
            }
        }

        twSizeExample = view.findViewById<TextView>(R.id.textSizeExample)
        twSizeExample.textSize = SettingsUtils.getLetterSizeSp()

        sbLetterSize = view.findViewById<SeekBar>(R.id.seekBar3)
        sbLetterSize.min = SettingsUtils.minLetterSizePx
        sbLetterSize.max = SettingsUtils.maxLetterSizePx
        sbLetterSize.setOnSeekBarChangeListener(this)
        sbLetterSize.setProgress(SettingsUtils.getLetterSizePx().toInt())

        val spinnerRead = view.findViewById<Spinner>(R.id.readingLang);
        val spinnerTrs = view.findViewById<Spinner>(R.id.translatingLang)

        spinnerRead.setSelection(0)
        spinnerTrs.setSelection(1)
        spinnerRead.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val langKeys = resources.getStringArray(R.array.langskey)
                SettingsUtils.readingLang = langKeys[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        spinnerRead.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val langKeys = resources.getStringArray(R.array.langskey)
                SettingsUtils.translatingLang = langKeys[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
//        val adapter = ArrayAdapter.createFromResource(context!!, R.array.langs,
//            android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter)

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
}