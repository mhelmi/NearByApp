package com.github.mhelmi.nearby

import androidx.preference.PreferenceManager
import androidx.test.platform.app.InstrumentationRegistry
import com.github.mhelmi.nearby.utils.LocationUpdateMode
import com.github.mhelmi.nearby.utils.SessionManager
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SessionManagerTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var sessionManager: SessionManager

    @Before
    fun setup() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sessionManager = SessionManager(sharedPreferences, sharedPreferences.edit())
    }

    @Test
    fun testPutAndGetLocationUpdateMode() {
        val testMode = LocationUpdateMode.SINGLE_UPDATE
        sessionManager.locationUpdateMode = testMode
        val mode = sessionManager.locationUpdateMode
        assertEquals(testMode, mode)
    }
}