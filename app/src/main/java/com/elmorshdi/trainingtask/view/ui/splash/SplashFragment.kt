package com.elmorshdi.trainingtask.view.ui.splash

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.elmorshdi.trainingtask.R
import com.elmorshdi.trainingtask.view.util.SharedPreferencesManager.getLoginValue
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    @Inject
lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        super.onViewCreated(view, savedInstanceState)
        val logged= getLoginValue(sharedPreferences)

        val motionLayout: MotionLayout = view.findViewById(R.id.motionLayout)
        motionLayout.startLayoutAnimation()
        motionLayout.setTransitionListener(object : TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                println("onTransitionStarted")
             }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {                println("onTransitionChange")

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {

                    println("onTransitionCompleted")

                    val action = if (logged) {
                        SplashFragmentDirections.actionSplashFragmentToMainFragment()
                    }else{
                        SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                    }
                    view.findNavController().navigate(action)
                }


            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
             }

        })


    }




}