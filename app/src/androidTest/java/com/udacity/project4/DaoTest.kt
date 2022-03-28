package com.udacity.project4

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.local.RemindersDao
import com.udacity.project4.locationreminders.data.local.RemindersDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DaoTest {

    private val reminderData = ReminderDTO(
        title = "Title Test",
        description = "Description Test",
        location = "SF",
        latitude = 2.22,
        longitude = 2.22,
        id = "RandomKeyRandomKeyRandomKeyForTesting"
    )

    private lateinit var database: RemindersDatabase
    private lateinit var dao: RemindersDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).build()

        dao = database.reminderDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertIntoDataBaseAndRetrieveByKey() = runBlockingTest {
        dao.saveReminder(reminderData)

        val savedReminder = dao.getReminderByLocation(reminderData.id)

        Truth.assertThat(savedReminder).isNotNull()
        Truth.assertThat(savedReminder?.title).isEqualTo(reminderData.title)
        Truth.assertThat(savedReminder?.description).isEqualTo(reminderData.description)
        Truth.assertThat(savedReminder?.location).isEqualTo(reminderData.location)
        Truth.assertThat(savedReminder?.longitude).isEqualTo(reminderData.longitude)
        Truth.assertThat(savedReminder?.latitude).isEqualTo(reminderData.latitude)
        Truth.assertThat(savedReminder?.id).isEqualTo(reminderData.id)
    }


}