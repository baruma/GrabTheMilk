
package com.udacity.project4

import android.app.Application
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.udacity.project4.locationreminders.RemindersActivity
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.local.LocalDB
import com.udacity.project4.locationreminders.data.local.RemindersLocalRepository
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.locationreminders.reminderslist.RemindersListViewModel
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import com.udacity.project4.util.DataBindingIdlingResource
import com.udacity.project4.util.monitorActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get


@RunWith(AndroidJUnit4::class)
@LargeTest
//END TO END test to black box test the app
class RemindersActivityTest :
    AutoCloseKoinTest() {// Extended Koin Test - embed autoclose @after method to close Koin after every test

    private lateinit var repository: ReminderDataSource
    private lateinit var appContext: Application
    private val dataBindingIdlingResource = DataBindingIdlingResource()
    private lateinit var viewModel : SaveReminderViewModel
    @get:Rule
    val activityRule = ActivityScenarioRule(RemindersActivity::class.java)

    /**
     * As we use Koin as a Service Locator Library to develop our code, we'll also use Koin to test our code.
     * at this step we will initialize Koin related code to be able to use it in out testing.
     */
    @Before
    fun init() {
        stopKoin() //stop the original app koin
        appContext = getApplicationContext()
        val myModule = module {
            viewModel {
                RemindersListViewModel(
                    appContext,
                    get() as ReminderDataSource
                )
            }
            single {
                SaveReminderViewModel(appContext, get(), Dispatchers.Main)
            }
            single { RemindersLocalRepository(get()) as ReminderDataSource }
            single { LocalDB.createRemindersDao(appContext) }
        }
        //declare a new koin module
        startKoin {
            modules(listOf(myModule))
        }
        //Get our real repository
        repository = get()
        viewModel = get()

        //clear the data to start fresh
        runBlocking {
            repository.deleteAllReminders()
        }


    }

    @Before
    fun registerIdlingResource() {
        dataBindingIdlingResource.monitorActivity(activityRule.scenario)
//        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
//        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun launchReminderActivityAndCreateAReminder() {
        val reminderDataItem = ReminderDataItem(
            "Test Title",
            "Test Description",
            "San Francisco",
            37.77493,
            -122.41942,
            "IHateEntitledSanFranciscanFoodies")

        onView(ViewMatchers.withId(R.id.addReminderFAB)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.addReminderFAB)).perform(click())

        val stringHint = appContext.getString(R.string.reminder_title)
        onView(ViewMatchers.withId(R.id.reminderTitle)).check(matches(withHint(stringHint)))

        onView(ViewMatchers.withId(R.id.reminderTitle)).perform(typeText(reminderDataItem.title))
        onView(ViewMatchers.withId(R.id.reminderDescription)).perform(typeText(reminderDataItem.description))
        viewModel.savePOI(PointOfInterest(LatLng(0.0, 0.0), "Fake Title", "Fake description"))

        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())

        onView(ViewMatchers.withId(R.id.saveReminderFAB)).perform(click())

        onView(ViewMatchers.withText(reminderDataItem.title)).check(matches(isDisplayed()))
        onView(ViewMatchers.withText(reminderDataItem.description)).check(matches(isDisplayed()))
    }

}

