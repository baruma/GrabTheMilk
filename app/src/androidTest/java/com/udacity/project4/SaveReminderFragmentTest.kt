//package com.udacity.project4
//
//import android.os.IBinder
//import android.view.WindowManager
//import androidx.test.espresso.Espresso
//import androidx.test.espresso.Root
//import androidx.test.espresso.action.ViewActions
//import androidx.test.espresso.assertion.ViewAssertions
//import androidx.test.espresso.matcher.ViewMatchers
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.filters.MediumTest
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.PointOfInterest
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import org.hamcrest.Description
//import org.hamcrest.TypeSafeMatcher
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//@ExperimentalCoroutinesApi
//@MediumTest
//class SaveReminderFragmentTest {
//    // Test if toast is on view
//
//    @Test
//    fun checkIfToastIsAccurte() {
//
//
//        Espresso.onView(ViewMatchers.withId(R.id.addReminderFAB))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//        Espresso.onView(ViewMatchers.withId(R.id.addReminderFAB)).perform(ViewActions.click())
//
//        val stringHint = appContext.getString(R.string.reminder_title)
//        Espresso.onView(ViewMatchers.withId(R.id.reminderTitle))
//            .check(ViewAssertions.matches(ViewMatchers.withHint(stringHint)))
//
//        Espresso.onView(ViewMatchers.withId(R.id.reminderTitle))
//            .perform(ViewActions.typeText(reminderDataItem.title))
//        Espresso.onView(ViewMatchers.withId(R.id.reminderDescription))
//            .perform(ViewActions.typeText(reminderDataItem.description))
//        viewModel.saveLocation(PointOfInterest(LatLng(0.0, 0.0), "Fake Title", "Fake description"))
//
//        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())
//
//        Espresso.onView(ViewMatchers.withId(R.id.saveReminder)).perform(ViewActions.click())
//
//        Espresso.onView(ViewMatchers.withText(reminderDataItem.title))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//        Espresso.onView(ViewMatchers.withText(reminderDataItem.description))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//    }
//}
//
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