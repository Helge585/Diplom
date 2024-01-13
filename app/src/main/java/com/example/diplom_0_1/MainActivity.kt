package com.example.diplom_0_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.diplom_0_1.db.DBManager
import com.example.diplom_0_1.db.DBUtils
import com.example.diplom_0_1.db.DictionaryDAO
import com.example.diplom_0_1.dialogfragments.ChooseDictionaryDialogFragment
import com.example.diplom_0_1.dialogfragments.ChooseTestDialogFragment
import com.example.diplom_0_1.dialogfragments.ChooseWordDialogFragment
import com.example.diplom_0_1.fragments.DictionariesFragment
import com.example.diplom_0_1.fragments.DictionaryFragment
import com.example.diplom_0_1.fragments.ReadingFragment
import com.example.diplom_0_1.fragments.TranslatingFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var currentDictionaryName = ""
    public lateinit var dbManager: DBManager
    private val chooseWordDialogFragment = ChooseWordDialogFragment()
    private val dictionariesChoosenFragmentDialog = ChooseDictionaryDialogFragment()
    private val chooseTestDialogFragment = ChooseTestDialogFragment()
    private var rF : ReadingFragment? = null
    private var dF : DictionariesFragment? = null
    private var dictionaryFragment : DictionaryFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbManager = DBManager(applicationContext)

        DBUtils.initDataBase(applicationContext)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()
        toolbar.setupWithNavController(navController, appBarConfiguration)

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavView.setupWithNavController(navController)
        //bottomNavView.visibility = View.INVISIBLE

//        val tF = supportFragmentManager.findFragmentById(R.id.translatingFragment) as TranslatingFragment
//
////        (supportFragmentManager.findFragmentById(R.id.readingFragment) as ReadingFragment)
////            .setTranslator(translator)
    }

    fun setCurrentDictionaryName(dictName : String) {
        currentDictionaryName = dictName
    }

    fun getCurrentDictionateName() : String {
        return currentDictionaryName
    }
    fun hideFragmentDialog() {
        rF?.let { it.setSuppressLayoutFlag(false) }
    }
    fun setOnDictionaryEditingFragment(_deF : DictionaryFragment) {
        dictionaryFragment = _deF
    }
    fun setOnReadingFragment(_rF : ReadingFragment) {
        rF = _rF
    }
    fun setOnDictionariesFragment(_dF : DictionariesFragment) {
        dF = _dF
    }
    fun showChooseWordFragment(word : String, translatings : List<String>) {
        rF?.let { it.setSuppressLayoutFlag(true) }
        val args = Bundle()

        args.putStringArray("translatings", translatings.toTypedArray())
        args.putString("word", word)
        chooseWordDialogFragment.arguments = args
        chooseWordDialogFragment.show(supportFragmentManager, "words")
        //rF?.let { it.setSuppressLayoutFlag(false) }
    }
    fun OnRecievedTranslatedWordFromReadingFragment(word : String) {
        val tF = supportFragmentManager.findFragmentById(R.id.translatingFragment) as TranslatingFragment
        tF.OnRecievedTranslatedWordFromMainActivity(word)
    }

    fun showDictionariesChoosenDialogFragment(firstWord : String, secondWord : String) {
        val args = Bundle()
        args.putString("firstWord", firstWord)
        args.putString("secondWord", secondWord)

        val dicts = DictionaryDAO.getAllDictionaries()
        val names = mutableListOf<String>()
        dicts.forEach { names.add(it.name) }
        args.putStringArray("dicts", names.toTypedArray())

        dictionariesChoosenFragmentDialog.arguments = args
        dictionariesChoosenFragmentDialog.show(supportFragmentManager, "dicts")
    }

    fun showTestChoosingFragmentDialog(dictName : String) {
        val args = Bundle()
        args.putString("dictName", dictName)
        chooseTestDialogFragment.arguments = args
        chooseTestDialogFragment.show(supportFragmentManager, "tests")
    }
}