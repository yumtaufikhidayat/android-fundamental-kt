package com.taufik.androidfundamental.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.taufik.androidfundamental.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TestingActivityTest{
    private val dummyVolume = "504.0"
    private val dummyCircumference = "100.0"
    private val dummySurfaceArea = "396.0"
    private val dummyLength = "12.0"
    private val dummyWidth = "7.0"
    private val dummyHeight = "6.0"
    private val emptyInput = ""
    private val fieldEmpty = "Field ini tidak boleh kosong"

    @Before
    fun setUp() {
        ActivityScenario.launch(TestingActivity::class.java)
    }

    @Test
    fun assertGetCircumference() {
        onView(withId(R.id.etLengthTest)).perform(typeText(dummyLength), closeSoftKeyboard())
        onView(withId(R.id.etWidthTest)).perform(typeText(dummyWidth), closeSoftKeyboard())
        onView(withId(R.id.etHeightTest)).perform(typeText(dummyHeight), closeSoftKeyboard())

        onView(withId(R.id.btnSaveTest)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSaveTest)).perform(click())

        onView(withId(R.id.btnCalculateCircumferenceTest)).check(matches(isDisplayed()))
        onView(withId(R.id.btnCalculateCircumferenceTest)).perform(click())

        onView(withId(R.id.tvResultTest)).check(matches(isDisplayed()))
        onView(withId(R.id.tvResultTest)).check(matches(withText(dummyCircumference)))
    }

    @Test
    fun assertGetSurfaceArea() {
        onView(withId(R.id.etLengthTest)).perform(typeText(dummyLength), closeSoftKeyboard())
        onView(withId(R.id.etWidthTest)).perform(typeText(dummyWidth), closeSoftKeyboard())
        onView(withId(R.id.etHeightTest)).perform(typeText(dummyHeight), closeSoftKeyboard())
        
        onView(withId(R.id.btnSaveTest)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSaveTest)).perform(click())
        
        onView(withId(R.id.btnCalculateSurfaceAreaTest)).check(matches(isDisplayed()))
        onView(withId(R.id.btnCalculateSurfaceAreaTest)).perform(click())
        
        onView(withId(R.id.tvResultTest)).check(matches(isDisplayed()))
        onView(withId(R.id.tvResultTest)).check(matches(withText(dummySurfaceArea)))
    }

    @Test
    fun assertGetVolume() {
        onView(withId(R.id.etLengthTest)).perform(typeText(dummyLength), closeSoftKeyboard())
        onView(withId(R.id.etWidthTest)).perform(typeText(dummyWidth), closeSoftKeyboard())
        onView(withId(R.id.etHeightTest)).perform(typeText(dummyHeight), closeSoftKeyboard())

        onView(withId(R.id.btnSaveTest)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSaveTest)).perform(click())

        onView(withId(R.id.btnCalculateVolumeTest)).check(matches(isDisplayed()))
        onView(withId(R.id.btnCalculateVolumeTest)).perform(click())

        onView(withId(R.id.tvResultTest)).check(matches(isDisplayed()))
        onView(withId(R.id.tvResultTest)).check(matches(withText(dummyVolume)))
    }

    //Pengecekan untuk empty input
    @Test
    fun assertEmptyInput() {
        // pengecekan input untuk length
        onView(withId(R.id.etLengthTest)).perform(typeText(emptyInput), closeSoftKeyboard())

        onView(withId(R.id.btnSaveTest)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSaveTest)).perform(click())

        onView(withId(R.id.etLengthTest)).check(matches(hasErrorText(fieldEmpty)))
        onView(withId(R.id.etLengthTest)).perform(typeText(dummyLength), closeSoftKeyboard())

        // pengecekan input untuk width
        onView(withId(R.id.etWidthTest)).perform(typeText(emptyInput), closeSoftKeyboard())

        onView(withId(R.id.btnSaveTest)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSaveTest)).perform(click())

        onView(withId(R.id.etWidthTest)).check(matches(hasErrorText(fieldEmpty)))
        onView(withId(R.id.etWidthTest)).perform(typeText(dummyWidth), closeSoftKeyboard())

        // pengecekan input untuk height
        onView(withId(R.id.etHeightTest)).perform(typeText(emptyInput), closeSoftKeyboard())

        onView(withId(R.id.btnSaveTest)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSaveTest)).perform(click())

        onView(withId(R.id.etHeightTest)).check(matches(hasErrorText(fieldEmpty)))
        onView(withId(R.id.etHeightTest)).perform(typeText(dummyHeight), closeSoftKeyboard())

        onView(withId(R.id.btnSaveTest)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSaveTest)).perform(click())
    }
}