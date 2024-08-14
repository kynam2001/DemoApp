package com.example.demoapp

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", sdk = [34])
class MainActivityTest {
    private lateinit var mainActivity: MainActivity
    @Before
    fun setUp(){
        mainActivity = Robolectric.buildActivity(MainActivity::class.java)
            .create().get()
    }
    @Test
    fun testPlus(){
        assertEquals(4, mainActivity.plus(2,2))
    }

}