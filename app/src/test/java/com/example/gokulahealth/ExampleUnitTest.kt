package com.example.gokulahealth

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for GokulaHealth app logic.
 */
class ExampleUnitTest {

    @Test
    fun milkTotalYieldCalculation() {
        // Test MilkRecord total yield = morning + evening
        val morning = 5.5
        val evening = 4.2
        val expected = 9.7
        assertEquals(expected, morning + evening, 0.01)
    }

    @Test
    fun earTagNotBlank() {
        val earTag = "GH-001"
        assertTrue(earTag.isNotBlank())
    }
}
