package com.example.demoapp

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//    private var mainActivity = Robolectric.buildActivity(MainActivity::class.java).create().get()
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2+2)
    }
}