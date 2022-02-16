package com.elmorshdi.trainingtask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.elmorshdi.trainingtask.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController


    }

    override fun onBackPressed() {
         Toast.makeText(this, "مش خارج \uD83D\uDE02\uD83D\uDE02", Toast.LENGTH_LONG).show()

    }
    }
