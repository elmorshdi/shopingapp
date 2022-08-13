package com.elmorshdi.trainingtask.view.util

import com.elmorshdi.trainingtask.helper.isEmailValid
import com.elmorshdi.trainingtask.helper.isValidPassword

object Util {
     fun validateLogin(
         user:String,
         password:String
     ):Boolean{
         if (!user.isEmailValid()){
             return false
         }
         if (!password.isValidPassword()){
             return false
         }
         return true
     }

}