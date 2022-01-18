package com.udacity.project4.locationreminders.savereminder

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.udacity.project4.R

class SaveReminderFragmentDirections private constructor() {
  companion object {
    fun actionSaveReminderFragmentToReminderListFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_saveReminderFragment_to_reminderListFragment)

    fun actionSaveReminderFragmentToMapsFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_saveReminderFragment_to_mapsFragment)
  }
}
