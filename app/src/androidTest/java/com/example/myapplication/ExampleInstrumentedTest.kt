package com.example.myapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myapplication.task1.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.myapplication", appContext.packageName)
    }


    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun listGoesOverTheFold() {

        onView(withText("Hello World!")/*withId(R.id.textView)*/).check(matches(isDisplayed()))

//        onView(withId(R.id.userNameET)).perform(typeText("UserName"))
//        onView(withId(R.id.passwordET)).perform(typeText("Password@123"),closeSoftKeyboard())
//        onView(withId(R.id.userNameET)).check(matches(withText("UserName")))

    }

    @Test
    fun checkClick() {
        onView(withId(R.id.checkBtn)).perform(click())

        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }

        onView(withText("Activity2")/*withId(R.id.textView)*/).check(matches(isDisplayed()))
    }



}