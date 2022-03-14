package com.udacity.project4.locationreminders

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.locationreminders.reminderslist.RemindersListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {

    private var dataSource = FakeDataSource()
    private var remindersListViewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(), dataSource)

//
//    @Test
//    fun shouldLoadReminders() {
//        val remindersListViewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(), FakeDataSource())
//    }

    @Test
    fun remindersUnableToDisplay() = runBlockingTest {
        dataSource.setShouldReturnError(true)
        remindersListViewModel.loadReminders()
        assertThat(remindersListViewModel.showSnackBar.getOrAwaitValue()).isEqualTo("Error")
    }
}