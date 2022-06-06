package com.example.mcommerceapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mcommerceapp.databinding.ActivityMainBinding
import com.example.mcommerceapp.view.FragmentContainer
import com.example.mcommerceapp.view.ui.feature_product.CategorizedProductActivity
import com.example.mcommerceapp.view.ui.home.HomeFragment
import com.example.mcommerceapp.view.ui.more.view.MoreFragment
import com.example.mcommerceapp.view.ui.profile.view.Profile
import com.example.mcommerceapp.view.ui.search.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        Log.e("create","Hello")
        return super.onCreateOptionsMenu(menu)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  setSupportActionBar(binding.topBar)

        this.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        val view: View = supportActionBar!!.customView
        val searchImage = view.findViewById<ImageView>(R.id.searchImage)

        searchImage.setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_profile, R.id.navigation_setting
            )
        )

        binding.navView.setOnItemSelectedListener () {
            Log.e("selected","hello")
            when(it.itemId){
                R.id.navigation_home -> {setCurrentFragment(Profile())
                    setCurrentFragment(FragmentContainer())
                }
                R.id.navigation_profile -> setCurrentFragment(Profile())
                R.id.navigation_setting -> setCurrentFragment(MoreFragment())


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()

        val sharedPreferences: SharedPreferences = getSharedPreferences("user", 0)
        val lang = sharedPreferences.getString("lan", "en")
        val locale = lang?.let { Locale(it) }
        if (locale != null) {
            Locale.setDefault(locale)
        }
        val resources: Resources = resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}