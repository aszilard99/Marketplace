package com.example.bazaar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {


    //TODO megoldani hogy az alkalmazast ne lehessen elforgatni

    //TODO lekezelni hogy jelezze ha nincs internet
    private lateinit var bottomNavigation : BottomNavigationView
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.loginFragment) {

                bottomNavigation.visibility = View.GONE
            } else {

                bottomNavigation.visibility = View.VISIBLE
            }
        }

        bottomNavigation.setOnItemSelectedListener (NavigationBarView.OnItemSelectedListener { menuItem ->
            bottomNavigation.menu.getItem(0).isChecked = true

            when(menuItem.itemId){
                R.id.myMarketMenuItem -> Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.myMarketFragment)
                R.id.myFaresMenuItem -> Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.myFaresFragment)
                R.id.timelineMenuItem -> Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.timelineFragment)
            }

            true
        })
    }

}