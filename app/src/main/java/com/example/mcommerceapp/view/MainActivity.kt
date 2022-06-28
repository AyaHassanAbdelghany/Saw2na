package com.example.mcommerceapp.view

import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivityMainBinding
import com.example.mcommerceapp.network.MyConnectivityManager
import com.example.mcommerceapp.view.ui.category.CategoryTabLayoutFragment
import com.example.mcommerceapp.view.ui.home.HomeFragment
import com.example.mcommerceapp.view.ui.more.view.MoreFragment
import com.example.mcommerceapp.view.ui.profile.view.Profile
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MyConnectivityManager.state.observe(this) {

            if (it) {
                selectedItem()
                binding.networkLayout.noNetworkLayout.visibility = View.INVISIBLE
                binding.mainLayout.visibility = View.VISIBLE

            } else {
                binding.networkLayout.noNetworkLayout.visibility = View.VISIBLE
                binding.mainLayout.visibility = View.INVISIBLE
            }
        }
        binding.navView.setOnItemSelectedListener {
            onOptionsItemSelected(it)
            when (it.itemId) {
                R.id.navigation_home -> setCurrentFragment((HomeFragment()))
                R.id.navigation_profile -> setCurrentFragment(Profile())
                R.id.navigation_setting -> setCurrentFragment(MoreFragment())
                R.id.navigation_category -> setCurrentFragment(CategoryTabLayoutFragment())
            }
            true
        }

        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(
            MyConnectivityManager.networkRequest,
            MyConnectivityManager.networkCallback
        )
    }

    override fun onBackPressed() {
        when (binding.navView.selectedItemId) {
            R.id.navigation_home -> finishAffinity()
            else -> {
                setCurrentFragment(HomeFragment())
                binding.navView.selectedItemId = R.id.navigation_home
            }
        }
    }


    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_activity_main, fragment)
            commit()
        }
    private fun selectedItem(){
        when (binding.navView.selectedItemId) {
            R.id.navigation_home ->
            {setCurrentFragment(HomeFragment())
                binding.navView.selectedItemId = R.id.navigation_home
            }
            R.id.navigation_profile ->
            {setCurrentFragment(Profile())

                binding.navView.selectedItemId = R.id.navigation_profile
            }
            R.id.navigation_setting ->
            {setCurrentFragment(MoreFragment())
                binding.navView.selectedItemId = R.id.navigation_setting
            }
            R.id.navigation_category -> {
                setCurrentFragment(CategoryTabLayoutFragment())
                binding.navView.selectedItemId = R.id.navigation_category
            }
        }
    }

}


