package com.elmorshdi.trainingtask.view.util

import org.junit.Assert.*
import org.junit.Test

class UtilTest{
    @Test
    fun `empty username return false`(){
        val result =Util.validateLogin(
            "",
            "123M@fghjk"
        )
        assertFalse(result)
    }

    @Test
    fun `wrong username return false`(){
        val result =Util.validateLogin(
            "yule",
            "123M@fghjk"
        )
        assertFalse(result)
    }
    @Test
    fun `wrong password return false`(){
        val result =Util.validateLogin(
            "hjk@ghjk.com",
            "123"
        )
        assertFalse(result)
    }
    @Test
    fun `valid password username return true`(){
        val result =Util.validateLogin(
            "mkm@jklll.com",
            "123M@fghjk"
        )
        assertTrue(result)
    }

}