package com.udacity.project4

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.local.LocalDB
import com.udacity.project4.locationreminders.data.local.RemindersLocalRepository
import com.udacity.project4.locationreminders.reminderslist.ReminderListFragment
import com.udacity.project4.locationreminders.reminderslist.RemindersListViewModel
import com.udacity.project4.util.DataBindingIdlingResource
import com.udacity.project4.util.monitorFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
//UI Testing
@MediumTest
class ReminderListFragmentTest {

    private lateinit var repository: ReminderDataSource
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

    @Before
    fun setup() {
        stopKoin()

        val appModule = module {
            viewModel {
                RemindersListViewModel(
                    ApplicationProvider.getApplicationContext(),
                    get() as ReminderDataSource
                )
            }

            single<ReminderDataSource> { RemindersLocalRepository(get()) }
            single { LocalDB.createRemindersDao(ApplicationProvider.getApplicationContext()) }
        }

        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(listOf(appModule))
        }

        repository = GlobalContext.get().koin.get()

        runBlocking {
            repository.deleteAllReminders()
        }
    }

// Testing the fragments’ navigation.
    @Test
    fun testNavigationToSaveReminderFragment() {
        // Create a mock NavController
        val mockNavController = mock(NavController::class.java)

        // Create a graphical FragmentScenario for the TitleScreen
        val reminderListScenario = launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(reminderListScenario)

        // Set the NavController property on the fragment
        reminderListScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        // Verify that performing a click prompts the correct Navigation action
        Espresso.onView(ViewMatchers.withId(R.id.addReminderFAB)).perform(ViewActions.click())
        verify(mockNavController).navigate(ActionOnlyNavDirections(R.id.to_save_reminder))
    }

    // Testing for Snackbar and Toast messages. - Figure out how to do this with Toast message being fed from ViewModel

}

object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

//    fun increment() {
//        countingIdlingResource.increment()
//    }
//
//    fun decrement() {
//        if (!countingIdlingResource.isIdleNow) {
//            countingIdlingResource.decrement()
//        }
//    }

}
