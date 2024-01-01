package com.example.diplom_0_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val translator: Translator = Translator()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    fun OnRecievedTranslatedWordFromReadingFragment(word : String) {
        val tF = supportFragmentManager.findFragmentById(R.id.translatingFragment) as TranslatingFragment
        tF.OnRecievedTranslatedWordFromMainActivity(word)
    }
}