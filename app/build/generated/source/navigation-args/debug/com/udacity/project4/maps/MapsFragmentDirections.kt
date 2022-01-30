package com.udacity.project4.maps

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.udacity.project4.R

class MapsFragmentDirections private constructor() {
  companion object {
    fun actionMapsFragmentToSaveReminderFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_mapsFragment_to_saveReminderFragment)
  }
}
