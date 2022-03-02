package com.elmorshdi.trainingtask.view.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.elmorshdi.trainingtask.Constant
import com.elmorshdi.trainingtask.R
import com.elmorshdi.trainingtask.view.ui.addproduct.AddItemFragmentDirections
import com.elmorshdi.trainingtask.view.ui.productview.ProductViewFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
lateinit var sharedPreferences: SharedPreferences
@Inject
lateinit var token:String
    private  var  doubleBackToExitPressedOnce :Boolean=false
    lateinit var navHostFragment:NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController

        doubleBackToExitPressedOnce= false



    }

    override fun onBackPressed() {
        when(NavHostFragment.findNavController(navHostFragment).currentDestination?.label){

            "fragment_add_item"->{
                val action = AddItemFragmentDirections.actionAddItemFragmentToMainFragment()
                NavHostFragment.findNavController(navHostFragment).navigate(action)
            }
            "ProductFragment"->{
                val action = ProductViewFragmentDirections.actionProductFragmentToMainFragment()
                NavHostFragment.findNavController(navHostFragment).navigate(action)
            }
            else->{
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
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }


}
