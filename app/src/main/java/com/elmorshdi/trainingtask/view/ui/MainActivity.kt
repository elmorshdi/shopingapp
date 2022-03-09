package com.elmorshdi.trainingtask.view.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.elmorshdi.trainingtask.R
import com.elmorshdi.trainingtask.view.ui.addproduct.AddItemFragmentDirections
import com.elmorshdi.trainingtask.view.ui.productview.ProductViewFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce: Boolean = false
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_Activity_nav_host_fragment) as NavHostFragment
        navHostFragment.navController

        doubleBackToExitPressedOnce = false


    }


    override fun onBackPressed() {
        when (NavHostFragment.findNavController(navHostFragment).currentDestination?.label) {

            resources.getString(R.string.label_fragment_add_item) -> {
                val action = AddItemFragmentDirections.actionAddItemFragmentToMainFragment()
                NavHostFragment.findNavController(navHostFragment).navigate(action)
            }
            resources.getString(R.string.label_fragment_product) -> {
                val action = ProductViewFragmentDirections.actionProductFragmentToMainFragment()
                NavHostFragment.findNavController(navHostFragment).navigate(action)
            }
            else -> {
                exit()
            }
        }
    }

    private fun exit() {
        if (doubleBackToExitPressedOnce) {

            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, " Please click BACK again to exit ", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }


}
