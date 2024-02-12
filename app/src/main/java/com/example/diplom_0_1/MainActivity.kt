package com.example.diplom_0_1

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.diplom_0_1.db.DBUtils
import com.example.diplom_0_1.fragments.DictionariesFragment
import com.example.diplom_0_1.fragments.DictionaryFragment
import com.example.diplom_0_1.fragments.TranslatingFragment
import com.example.diplom_0_1.test.TestUtils
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var dictionariesFragment : DictionariesFragment? = null
    private var dictionaryFragment : DictionaryFragment? = null
    var translatingFragment : TranslatingFragment? = null

    lateinit var bottomNavView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (TestUtils.themeMode == 1) {
            setTheme(R.style.MyTheme)
        }
        setContentView(R.layout.activity_main)

        DBUtils.initDataBase(applicationContext)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()
        toolbar.setupWithNavController(navController, appBarConfiguration)

        bottomNavView = findViewById(R.id.bottom_nav)
        bottomNavView.setupWithNavController(navController)

        translatingFragment = supportFragmentManager.findFragmentById(R.id.translatingFragment) as TranslatingFragment

        val dm = resources.displayMetrics

        Log.i("Main Activity", "width = " + dm.widthPixels)
        Log.i("Main Activity", "height = " + dm.heightPixels)
        Log.i("Main Activity", "xdpi = " + dm.xdpi)
        Log.i("Main Activity", "ydpi = " + dm.ydpi)

        val paint = Paint()
        paint.textSize = 42F // Устанавливаем размер шрифта из ресурсов
        val fontMetrics = paint.fontMetrics
        val textHeightPx = fontMetrics.bottom - fontMetrics.top
        Log.i("Main Activity", "letter height = " + textHeightPx)
        val textWidthPx = paint.measureText("A")
        Log.i("Main Activity", "letter width = " + textWidthPx)

        TestUtils.setPageSize(dm.widthPixels, dm.heightPixels, textWidthPx, textHeightPx)
    }

    fun setOnDictionaryEditingFragment(_deF : DictionaryFragment) {
        dictionaryFragment = _deF
    }

    fun setOnDictionariesFragment(_dF : DictionariesFragment) {
        dictionariesFragment = _dF
    }
    fun updateTranslatingFragment(word: String, sentence: String) {
        translatingFragment?.updateTranslating(word, sentence)
    }

    fun updateDictionaryFragment(testsWordsType: TestUtils.TestWordType,
                                 testDirectionType: TestUtils.TestDirectionType, wordsCount: Int) {
        dictionaryFragment?.updateTestsList(testsWordsType, testDirectionType, wordsCount)
    }
}