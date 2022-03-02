package com.elmorshdi.trainingtask.view.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.elmorshdi.trainingtask.Constant
import com.elmorshdi.trainingtask.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
lateinit var sharedPreferences: SharedPreferences
@Inject
lateinit var token:String
    private  var  doubleBackToExitPressedOnce :Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
        doubleBackToExitPressedOnce= false

        val name = sharedPreferences.getString(Constant.TOKENN, "")
        Log.d("token","name:$name")
        Log.d("token","token:$token")

    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {

            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)

    }


}
