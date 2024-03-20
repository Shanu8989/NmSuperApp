package com.shanu.nmsuperapp.presentation.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.shanu.helpmodule.HelpFragment
import com.shanu.homemodule.HomeFragment
import com.shanu.lockermodule.LockerLandingFragment
import com.shanu.nmsuperapp.R
import com.shanu.nmsuperapp.databinding.ActivityLandingBinding
import com.shanu.servicemodule.ServicesFragment
import com.shanu.settingsmodule.SettingsFragment

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding
    private lateinit var toolbar: Toolbar
    private lateinit var headerIv: AppCompatImageView
    private var isSearchExpanded = false
    private lateinit var bottomNavView: BottomNavigationView

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.landingToolbar
        headerIv = binding.headerIv

        supportActionBar?.hide()
        toolbar.title = ""

        //Remove Back action
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val firstFragment = HomeFragment()
        firstFragment.registerHomeModuleDataCallback {
            this@LandingActivity.onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        navigateToLoginActivity()
                    }
                })
        }

        val secondFragment = LockerLandingFragment()
        secondFragment.registerLockerModuleDataCallback {
            this@LandingActivity.onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        navigateToLoginActivity()
                    }
                })
        }

        val thirdFragment = ServicesFragment()
        thirdFragment.registerServicesModuleDataCallback {
            this@LandingActivity.onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        navigateToLoginActivity()
                    }
                })
        }

        val fourthFragment = SettingsFragment()
        fourthFragment.registerSettingsModuleDataCallback {
            this@LandingActivity.onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        navigateToLoginActivity()
                    }
                })
        }

        val fifthFragment = HelpFragment()
        fifthFragment.registerHelpModuleDataCallback {
            this@LandingActivity.onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        navigateToLoginActivity()
                    }
                })
        }

        setCurrentFragment(firstFragment, "")

        bottomNavView = binding.bottomNavigationView

//        customize Bottom Navigation View
        val menu = bottomNavView.menu
        for (i in 0 until menu.size()) {
            val menuItem = menu.getItem(i)
            val customView = LayoutInflater.from(this).inflate(R.layout.menu_item_layout, null)
            menuItem.actionView = customView
        }

        // Set an item selected listener to change the background of the selected item
        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_menu_item -> setCurrentFragment(firstFragment, null)
                R.id.locker_menu_item -> setCurrentFragment(secondFragment, null)
                R.id.services_menu_item -> setCurrentFragment(thirdFragment, null)
                R.id.settings_menu_item -> setCurrentFragment(fourthFragment, null)
                R.id.help_menu_item -> setCurrentFragment(fifthFragment, null)
            }
            true
        }

        bottomNavView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
    }

    private fun navigateToLoginActivity() {
        Intent(this@LandingActivity, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.toolbar_menu, menu)

        // below line is to get our menu item.
        val searchItem: MenuItem = menu.findItem(R.id.action_search)

        // getting search view of our item.
        val searchView: SearchView = searchItem.actionView as SearchView

        //ToDo: Fix search expansion. Refer Neom RAB
        searchView.setOnSearchClickListener {
            if (!isSearchExpanded) {
                headerIv.visibility = View.GONE
                isSearchExpanded = true
            } else {
                headerIv.visibility = View.VISIBLE
                isSearchExpanded = false
            }
        }

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                return false
            }
        })
        return true

    }

    private fun setCurrentFragment(fragment: Fragment, title: String?) {
        if (title != null) {
            toolbar.title = title
        } else {
            toolbar.title = ""
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commitAllowingStateLoss()
        }
    }

}