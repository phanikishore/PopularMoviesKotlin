package com.kishore.tmdbapp.utils

import android.util.Patterns
import org.junit.Assert.*
import org.junit.Test
import java.util.*
import javax.annotation.MatchesPattern

class DateExtensionsTest {

    @Test
    fun test_getMidNightCalendar() {
        val calendar = getMidNightCalendar()
        assertEquals(
            "0:0:0",
            "${calendar.get(Calendar.HOUR)}:${calendar.get(Calendar.MINUTE)}:${calendar.get(Calendar.SECOND)}"
        )
    }

    @Test
    fun test_StringDate_to_different_formatted_StringDate() {
        val date = "2021-6-14"
        val actualValue = date.toStringFormatted("yyyy-MM-dd", "MMM dd,yyyy")
        println(actualValue)
        assertNotNull(actualValue)
    }

    @Test
    fun test_StringDate_to_Date() {
        val date = "2021-06-14"
        val actualValue = date.toDate("yyyy-MM-dd")
        assertNotNull(actualValue)
    }

    @Test
    fun test_Date_toString() {
        val date = Date(1623263400000)
        val actualValue = date.toStringFormatted("MMM dd, yyyy")
        assertEquals("Jun 10, 2021", actualValue)
    }

    @Test
    fun test_Date_toString_NotEqual() {
        val date = Calendar.getInstance().time
        val actualValue = date.toStringFormatted("MMM dd, yyyy")
        assertNotEquals("Jun 10, 2021", actualValue)
    }

    @Test
    fun test_timeInMillis_to_Date() {
        val date = 1623263400000L
        val actualValue = date.toDate()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = date
        }
        println(actualValue)
        assertEquals(calendar.time, actualValue)
    }

    @Test
    fun test_timeInMillis_to_formatted_StringDate() {
        val date = 1623263400000L
        val actualValue = date.toFormattedString("yyyy-MMM-dd")
        val actualValue2 = date.toFormattedString("dd-MM-yyyy")
        println(actualValue)
        println(actualValue2)
        assertEquals("2021-Jun-10", actualValue)
        assertEquals("10-06-2021", actualValue2)
    }
}