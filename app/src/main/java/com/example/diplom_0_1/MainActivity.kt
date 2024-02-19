package com.example.diplom_0_1

import android.content.res.Resources.Theme
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.ThemeUtils
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.diplom_0_1.db.DBUtils
import com.example.diplom_0_1.fragments.DictionariesFragment
import com.example.diplom_0_1.fragments.DictionaryFragment
import com.example.diplom_0_1.fragments.TranslatingFragment
import com.example.diplom_0_1.setting.SettingsUtils
import com.example.diplom_0_1.test.TestUtils
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var dictionariesFragment : DictionariesFragment? = null
    private var dictionaryFragment : DictionaryFragment? = null
    var translatingFragment : TranslatingFragment? = null

    lateinit var bottomNavView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(SettingsUtils.getTheme())
        super.onCreate(savedInstanceState)

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

        SettingsUtils.setDisplayMetrics(resources.displayMetrics)
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