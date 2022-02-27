package com.udacity.project4.locationreminders

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.reminderslist.RemindersListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {

    @Test
    fun shouldLoadReminders() {
        val remindersListViewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(), FakeDataSource())
    }


}