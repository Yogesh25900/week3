package com.example.week3.UI.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.week3.ui.fragment.CartFragment
import com.example.week3.ui.fragment.HomeFragment
import com.example.week3.ui.fragment.ProfileFragment
import com.example.week3.R
import com.example.week3.databinding.ActivityNavBinding

class NavActivity : AppCompatActivity() {



        lateinit var navigationBinding: ActivityNavBinding

        private fun replaceFragment(fragment: Fragment) {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.frameLayout, fragment)
            fragmentTransaction.commit()
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            navigationBinding = ActivityNavBinding.inflate(layoutInflater)

            setContentView(navigationBinding.root)

            replaceFragment(HomeFragment())
            navigationBinding.buttomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menuHome -> replaceFragment(HomeFragment())
                    R.id.menuCart -> replaceFragment(CartFragment())
                    R.id.menuProfile -> replaceFragment(ProfileFragment())
                    else -> {}
                }
                true
            }
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }
    }