package com.udacity.project4.locationreminders

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveReminderViewModelTest {

    private var dataSource = FakeDataSource()
    private var saveReminderViewModel = SaveReminderViewModel(
        ApplicationProvider.getApplicationContext(),
        dataSource,
        Dispatchers.Main
    )

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testLoading() = runBlockingTest {
        mainCoroutineRule.pauseDispatcher()
        saveReminderViewModel.saveReminder(
            ReminderDataItem(
                "Test Title",
                "Test Description",
                "SF",
                22.22,
                22.22,
                "RandomKey"
            )
        )
        assertThat(saveReminderViewModel.showLoading.getOrAwaitValue()).isTrue()
        mainCoroutineRule.resumeDispatcher()
        assertThat(saveReminderViewModel.showLoading.getOrAwaitValue()).isFalse()

    }

    @Test
    fun testOnClear() = runBlockingTest {
        saveReminderViewModel.onClear()
        Assert.assertNull(saveReminderViewModel.reminderTitle.value)
        Assert.assertNull(saveReminderViewModel.reminderDescription.value)
        Assert.assertNull(saveReminderViewModel.longitude.value)
        Assert.assertNull(saveReminderViewModel.latitude.value)
        Assert.assertNull(saveReminderViewModel.selectedPOI.value)
        Assert.assertNull(saveReminderViewModel.reminderSelectedLocationStr.value)
    }

    @Test
    fun testSaveReminder() = runBlockingTest {
        dataSource.saveReminder(
            ReminderDTO(
                "Test Title", "Test Description", "SF",
                22.22, 22.22, "RandomStringID"
            )
        )
    }

    @Test
    fun savePOI() = runBlockingTest {
        val poi = PointOfInterest(LatLng(22.22, 22.22), "test string", "another test string")
        saveReminderViewModel.savePOI(poi)
        Assert.assertEquals(saveReminderViewModel.poi, poi)
        Assert.assertEquals(saveReminderViewModel.reminderSelectedLocationStr.value, poi.name)
        Assert.assertEquals(saveReminderViewModel.latitude.value, poi.latLng.latitude)
        Assert.assertEquals(saveReminderViewModel.latitude.value, poi.latLng.longitude)
    }

    @Test
    fun saveCustomLocation() = runBlockingTest {
        val coordinates = LatLng(22.22, 22.22)

        saveReminderViewModel.saveCustomLocation(coordinates)
        Assert.assertEquals(saveReminderViewModel.selectedPOI.value!!.latLng, coordinates)
        Assert.assertEquals(saveReminderViewModel.reminderSelectedLocationStr.value, SaveReminderViewModel.customPinName)
        Assert.assertEquals(saveReminderViewModel.latitude.value, coordinates.latitude)
        Assert.assertEquals(saveReminderViewModel.longitude.value, coordinates.longitude)
    }

    @Test
    fun testSaveToDataSource() = runBlockingTest {
        val reminder =  ReminderDataItem(
            "Test Title",
            "Test Description",
            "SF",
            22.22,
            22.22,
            "RandomKey"
        )
        saveReminderViewModel.saveReminder(
           reminder
        )

        Assert.assertEquals(reminder.title, dataSource.lastSavedReminder!!.title)
        Assert.assertEquals(reminder.description, dataSource.lastSavedReminder!!.description)
        Assert.assertEquals(reminder.location, dataSource.lastSavedReminder!!.location)
        Assert.assertEquals(reminder.latitude, dataSource.lastSavedReminder!!.latitude)
        Assert.assertEquals(reminder.longitude, dataSource.lastSavedReminder!!.longitude)
        Assert.assertEquals(reminder.id, dataSource.lastSavedReminder!!.id)

    }

    @Test
    fun testReminderSnackbar() = runBlockingTest {
        val reminder = ReminderDataItem("Title", "Description", "SF", 22.22, 22.22, "Key")
        saveReminderViewModel.showSnackBar.observeForever { }
        saveReminderViewModel.saveReminder(reminder)
        Assert.assertEquals(saveReminderViewModel.showSnackBar.value, "Reminder Saved!")
    }

//    @RunWith(RobolectricTestRunner::class)
//    class ToastTest {
//        private lateinit var scenario: FragmentScenario<SaveReminderFragment>
//
//        @Test
//        fun shotToastTest() {
//            FragmentScenario.launchInContainer(SaveReminderFragment::class.java, null, R.style.AppTheme, null)
//            Assert.assertEquals(ShadowToast.getTextOfLatestToast(), "Hi")
//        }
//    }

}

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}

