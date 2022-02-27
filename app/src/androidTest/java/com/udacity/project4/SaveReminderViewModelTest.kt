package com.udacity.project4

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.FakeDataSource
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import javax.sql.DataSource

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveReminderViewModelTest {

//    private lateinit var fakeDataSource: FakeDataSource

    private var dataSource: ReminderDataSource = FakeDataSource()
    private var saveReminderViewModel = SaveReminderViewModel(ApplicationProvider.getApplicationContext(), dataSource)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    @ExperimentalCoroutinesApi
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
    fun testSuccess() {
        Assert.assertTrue(true)
    }

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