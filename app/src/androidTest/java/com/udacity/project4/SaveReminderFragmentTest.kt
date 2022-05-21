package com.udacity.project4

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.locationreminders.savereminder.SaveReminderFragment
import com.udacity.project4.util.DataBindingIdlingResource
import com.udacity.project4.util.monitorFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


// NONE PASSED ON 29
// Passed on 31
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@MediumTest
class SaveReminderFragmentTest {

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun registerIdlingResources(): Unit = IdlingRegistry.getInstance().run {
        register(EspressoIdlingResource.countingIdlingResource)
        register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(): Unit = IdlingRegistry.getInstance().run {
        unregister(EspressoIdlingResource.countingIdlingResource)
        unregister(dataBindingIdlingResource)
    }

    @Test
    fun titleNotEnteredSnackbar() {
        val saveReminderScenario = launchFragmentInContainer<SaveReminderFragment>(Bundle(), R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(saveReminderScenario)

        onView(withId(R.id.saveReminderFAB)).perform(ViewActions.click())
        val titleIssueString = "Please enter title"
        onView(withText(titleIssueString))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun descriptionNotEnteredSnackbar() {
        val saveReminderScenario = launchFragmentInContainer<SaveReminderFragment>(Bundle(), R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(saveReminderScenario)

        val titleString = "Title Test"

        onView(withId(R.id.reminderTitle)).perform(ViewActions.typeText(titleString))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.saveReminderFAB)).perform(ViewActions.click())
        onView(withText(R.string.empty_desription_error)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun locationNotSelectedSnackbar() {
        val saveReminderScenario = launchFragmentInContainer<SaveReminderFragment>(Bundle(), R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(saveReminderScenario)

        val titleString = "Title Test"
        val descriptionString = "Description Test"

        onView(withId(R.id.reminderTitle)).perform(ViewActions.typeText(titleString))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.reminderDescription)).perform(ViewActions.typeText(descriptionString))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.saveReminderFAB)).perform(ViewActions.click())
        onView(withText(R.string.err_select_location)).check(matches(ViewMatchers.isDisplayed()))
    }
}

//class ToastMatcher : TypeSafeMatcher<Root?>() {
//
//    override fun describeTo(description: Description?) {
//        description?.appendText("is toast")
//    }
//
//    override fun matchesSafely(item: Root?): Boolean {
//        val type: Int? = item?.getWindowLayoutParams()?.get()?.type
//        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
//            val windowToken: IBinder = item.getDecorView().getWindowToken()
//            val appToken: IBinder = item.getDecorView().getApplicationWindowToken()
//            if (windowToken === appToken) { // means this window isn't contained by any other windows.
//                return true
//            }
//        }
//        return false
//    }
//
//}