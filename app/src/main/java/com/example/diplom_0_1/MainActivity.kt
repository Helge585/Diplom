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
import com.example.diplom_0_1.dialogfragments.ChooseTestSettingsDialogFragment
import com.example.diplom_0_1.dialogfragments.CreateDictionaryDialogFragment
import com.example.diplom_0_1.dialogfragments.ShowDictionaryInformationDialogFragment
import com.example.diplom_0_1.dictionary.Dictionary
import com.example.diplom_0_1.fragments.DictionariesFragment
import com.example.diplom_0_1.fragments.DictionaryFragment
import com.example.diplom_0_1.fragments.ReadingFragment
import com.example.diplom_0_1.fragments.TranslatingFragment
import com.example.diplom_0_1.test.TestUtils
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var currentDictionaryName = ""
    public lateinit var dbManager: DBManager

   // private val chooseWordDialogFragment = ChooseWordDialogFragment()
    private val dictionariesChoosenFragmentDialog = ChooseDictionaryDialogFragment()
    private val testSettingsDialogFragment = ChooseTestSettingsDialogFragment()
    private val chooseTestDialogFragment = ChooseTestDialogFragment()
    //private val showDictionaryInformationDialogFragment = ShowDictionaryInformationDialogFragment()
    private val createDictionaryDialogFragment = CreateDictionaryDialogFragment()

    private var rF : ReadingFragment? = null
    private var dF : DictionariesFragment? = null
    private var dictionaryFragment : DictionaryFragment? = null
    var translatingFragment : TranslatingFragment? = null

    lateinit var bottomNavView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (TestUtils.themeMode == 1) {
            setTheme(R.style.MyTheme)
        }
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

        bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavView.setupWithNavController(navController)

        translatingFragment = supportFragmentManager.findFragmentById(R.id.translatingFragment) as TranslatingFragment
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
//    fun showChooseWordFragment(word: String, translatings: List<Translating>) {
//        rF?.let { it.setSuppressLayoutFlag(true) }
//        val args = Bundle()
//
//        args.putStringArray("translatings", translatings.toTypedArray())
//        args.putString("word", word)
//        chooseWordDialogFragment.arguments = args
//        chooseWordDialogFragment.show(supportFragmentManager, "words")
//        //rF?.let { it.setSuppressLayoutFlag(false) }
//    }
    fun OnRecievedTranslatedWordFromReadingFragment(word: String, sentence: String) {
//        val tF = supportFragmentManager.findFragmentById(R.id.translatingFragment) as TranslatingFragment
        translatingFragment?.OnRecievedTranslatedWordFromMainActivity(word, sentence)
    }

    fun showDictionariesChoosenDialogFragment(
        firstWord: String,
        secondWord: String,
        checkedSentence: String
    ) {
        val args = Bundle()
        args.putString("firstWord", firstWord)
        args.putString("secondWord", secondWord)
        args.putString("example", checkedSentence)
        val dicts = DictionaryDAO.getAllDictionaries()
        val names = mutableListOf<String>()
        dicts.forEach { names.add(it.name) }
        args.putStringArray("dicts", names.toTypedArray())

        dictionariesChoosenFragmentDialog.arguments = args
        dictionariesChoosenFragmentDialog.show(supportFragmentManager, "dicts")
    }

    fun showTestChoosingFragmentDialog(dict : Dictionary) {
        val args = Bundle()
        args.putString("dictName", dict.name)
        chooseTestDialogFragment.arguments = args
        chooseTestDialogFragment.show(supportFragmentManager, "tests")
    }

    fun showTestSettingsDialogFragment(allWordsCount : Int, newWordsCount : Int, wrongGuessWordsCount : Int) {
        val args = Bundle()
        args.putInt("allWordsCount", allWordsCount)
        args.putInt("newWordsCount", newWordsCount)
        args.putInt("wrongGuessWordsCount", wrongGuessWordsCount)
        testSettingsDialogFragment.arguments = args
        testSettingsDialogFragment.show(supportFragmentManager, "testSettings")
    }

    fun showCreateDictionaryDialogFragment() {
        createDictionaryDialogFragment.show(supportFragmentManager, "createDict")
    }
//    fun showDictionaryInformationDialogFragment(args : Bundle) {
//        showDictionaryInformationDialogFragment.arguments = args
//        showDictionaryInformationDialogFragment.show(supportFragmentManager, "dictInfo")
//    }
    fun updateDictionaryFragment(testsWordsType: TestUtils.TestWordType,
                                 testDirectionType: TestUtils.TestDirectionType, wordsCount: Int) {
        dictionaryFragment?.updateTestsList(testsWordsType, testDirectionType, wordsCount)
    }
}