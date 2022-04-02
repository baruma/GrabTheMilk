package com.udacity.project4.locationreminders

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.locationreminders.reminderslist.RemindersListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {

    private var dataSource = FakeDataSource()
    private var remindersListViewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(), dataSource)

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
    fun remindersUnableToDisplay() = runBlockingTest {
        dataSource.setShouldReturnError(true)
        remindersListViewModel.loadReminders()
        assertEquals(remindersListViewModel.showLoading.getOrAwaitValue(), false)
        assertEquals(remindersListViewModel.showErrorMessage.getOrAwaitValue(), "There is no data to display due to an error.")
    }
}