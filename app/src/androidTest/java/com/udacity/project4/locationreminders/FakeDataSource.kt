package com.udacity.project4.locationreminders

import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(var reminders: MutableList<ReminderDTO>? = mutableListOf()) :
    ReminderDataSource {

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        // Look at the original getReminders
        // Read up on the data types of Result and MutableList, etc.

        var reminderDTO = ReminderDTO(
            "Fake Title",
            "Fake Description",
            "SF",
            22.22,
            22.22,
            "RandomStringID"
        )

        return Result.Success(listOf(reminderDTO))

    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders!!.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        var reminderDTO = ReminderDTO(
            "Fake Title",
            "Fake Description",
            "SF",
            22.22,
            22.22,
            "RandomStringID"
        )

        return Result.Success(reminderDTO)
    }

    override suspend fun deleteAllReminders() {
        reminders = mutableListOf() // gives out blank list
    }


}