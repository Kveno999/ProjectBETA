package com.example.agro_town.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.NavAction
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.agro_town.R
import com.example.agro_town.databinding.ActivityDashboardBinding
import com.example.agro_town.messages.MainActivityMessages
import com.example.agro_town.ui.fragments.DashboardFragment
import com.example.agro_town.ui.fragments.OrdersFragment
import com.example.agro_town.ui.fragments.ProductsFragment
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationBarView

class DashboardActivity : BaseActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_products, R.id.navigation_dashboard, R.id.navigation_orders, R.id.action_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
                /*R.id.navigation_dashboard -> {
                    startActivity(Intent(this, DashboardFragment::class.java))
                }
                R.id.navigation_products -> {
                    startActivity(Intent(this, ProductsFragment::class.java))
                }
                R.id.navigation_orders -> {
                    startActivity(Intent(this, OrdersFragment::class.java))
                }*/
            }

            true
        }






    }



    override fun onBackPressed() {
        doubleBckToExit()
    }

}