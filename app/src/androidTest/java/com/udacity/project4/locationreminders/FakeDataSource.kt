package com.udacity.project4.locationreminders

import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

// Code should NOT be past the line to the right ->
// Indent if code runs past it

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(var reminders: MutableList<ReminderDTO>? = mutableListOf()) :
    ReminderDataSource {


    var lastSavedReminder: ReminderDTO? = null

    private var shouldReturnError = false

    fun setShouldReturnError(shouldReturn: Boolean) {
        this.shouldReturnError = shouldReturn
    }


    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        // Look at the original getReminders
        // Read up on the data types of Result and MutableList, etc.

        if (shouldReturnError) {
            return Result.Error("Error")
        }

        return try {
            var reminderDTO = ReminderDTO(
                "Fake Title",
                "Fake Description",
                "SF",
                22.22,
                22.22,
                "RandomStringID"
            )

            return Result.Success(listOf(reminderDTO))
        } catch(exception: Exception) {
            Result.Error(exception.localizedMessage)
        }

    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders!!.add(reminder)
        lastSavedReminder = reminder
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        if (shouldReturnError) {
            Result.Error("Error")
        }
        return try {
            var reminderDTO = ReminderDTO(
                "Fake Title",
                "Fake Description",
                "SF",
                22.22,
                22.22,
                "RandomStringID"
            )

            return Result.Success(reminderDTO)
        } catch (exception: Exception) {
            Result.Error(exception.localizedMessage)
        }
     }


    override suspend fun deleteAllReminders() {
        reminders = mutableListOf() // gives out blank list
    }


}