package com.udacity.project4

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.data.local.RemindersDatabase
import com.udacity.project4.locationreminders.data.local.RemindersLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Passed on both API 29 and 30 since 5/20

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class TasksLocal
class LocalRepositoryTest {

    private lateinit var database: RemindersDatabase
    private lateinit var repository: RemindersLocalRepository

    private var shouldReturnFalse = false

    fun setReturnError(value: Boolean) {
        shouldReturnFalse = value
    }

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val reminder = ReminderDTO (
        title = "Title Test",
        description = "Description Test",
        location = "SF",
        latitude = 2.22,
        longitude = 2.22,
        id = "IamakeyIamakeyIamakey"
    )

    @Before
    fun setup() {
        // Using an in-memory database for testing, because it doesn't survive killing the process.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        repository = RemindersLocalRepository(database.reminderDao(), Dispatchers.Main)
        setReturnError(false)
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun saveReminderSuccess() = runBlocking {
        repository.saveReminder(reminder)

//        val reminder = repository.saveReminder(reminder)
        val result = repository.getReminders()
        Truth.assertThat(result).isInstanceOf(Result.Success::class.java)

        result as Result.Success

        Truth.assertThat(result.data).isNotEmpty()
        Truth.assertThat(result.data).hasSize(1)

    }

    @Test
    fun getReminderSuccess() = runBlocking {
        repository.saveReminder(reminder)

        val result = repository.getReminderByID(reminder.id)
        Truth.assertThat(result).isInstanceOf(Result.Success::class.java)

        result as Result.Success

        Truth.assertThat(result.data).isNotNull()
        Truth.assertThat(result.data.title).isEqualTo(reminder.title)
        Truth.assertThat(result.data.description).isEqualTo(reminder.description)
        Truth.assertThat(result.data.location).isEqualTo(reminder.location)
        Truth.assertThat(result.data.latitude).isEqualTo(reminder.latitude)
        Truth.assertThat(result.data.longitude).isEqualTo(reminder.longitude)
        Truth.assertThat(result.data.id).isEqualTo(reminder.id)
    }

    @Test
    fun getRemindersFailure() = runBlocking {
        setReturnError(true)

        val result = repository.getReminderByID(reminder.id)
        Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
        Truth.assertThat((result as Result.Error).message).isEqualTo("Reminder not found!")
    }

//    override suspend fun getReminders(): Result<List<ReminderDTO>> = withContext(ioDispatcher) {
//        return@withContext try {
//            Result.Success(remindersDao.getReminders())
//        } catch (ex: Exception) {
//            Result.Error(ex.localizedMessage)
//        }
//    }

}