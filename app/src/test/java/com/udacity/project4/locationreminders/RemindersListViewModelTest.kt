package com.udacity.project4.locationreminders

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.R
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.reminderslist.RemindersListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private var dataSource = FakeDataSource()
    private var remindersListViewModel =
        RemindersListViewModel(ApplicationProvider.getApplicationContext(), dataSource)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun shouldLoadReminders() = runBlockingTest {
        remindersListViewModel.loadReminders()
        assertEquals(remindersListViewModel.showLoading.getOrAwaitValue(), false)
        assertThat(remindersListViewModel.remindersList.getOrAwaitValue()).hasSize(1)
    }

    @Test
    fun failToLoadReminders() = runBlockingTest {
        dataSource.setShouldReturnError(true)
        remindersListViewModel.loadReminders()
        assertEquals(remindersListViewModel.showErrorMessage.getOrAwaitValue(), context.getString(R.string.reminder_list_empty_error_message))
    }

    @Test
    fun noRemindersToDisplayToastTest() = runBlockingTest {
        val mockedDataSource = mock(ReminderDataSource::class.java)
        whenever(mockedDataSource.getReminders()).thenReturn(Result.Success(mutableListOf()))
        val viewModelWithMock =
            RemindersListViewModel(ApplicationProvider.getApplicationContext(), mockedDataSource)

        viewModelWithMock.loadReminders()

        assertEquals(viewModelWithMock.showLoading.getOrAwaitValue(), false)
        assertEquals(
            viewModelWithMock.showToast.getOrAwaitValue(),
            context.getString(R.string.empty_reminder_message)
        )
    }

}