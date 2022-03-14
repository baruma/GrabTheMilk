package com.udacity.project4.locationreminders.savereminder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.PointOfInterest
import com.udacity.project4.R
import com.udacity.project4.base.BaseViewModel
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class SaveReminderViewModel(
    val app: Application,
    private val dataSource: ReminderDataSource,
    private val dispatcher: CoroutineDispatcher
) :
    BaseViewModel(app) {

    var showToastLiveData = MutableLiveData<String>()

    val reminderTitle = MutableLiveData<String>()
    val reminderDescription = MutableLiveData<String>()
    var reminderSelectedLocationStr = MutableLiveData<String>()
    val latitude = MutableLiveData<Double>()
    val longitude = MutableLiveData<Double>()
    var selectedPOI = MutableLiveData<PointOfInterest>()

    var location: PointOfInterest? = null


    // test passed
    fun onClear() {
        reminderTitle.value = null
        reminderDescription.value = null
        reminderSelectedLocationStr.value = null
        selectedPOI.value = null
        latitude.value = null
        longitude.value = null
    }

//    fun validateAndSaveReminder(reminderData: ReminderDataItem) {
//        if (validateEnteredData(reminderData)) {
//            saveReminder(reminderData)
//        }
//    }

    fun saveLocation(location: PointOfInterest) {
        this.location = location
//        selectedPOI.value = location
//        reminderSelectedLocationStr.value = location.name
//        latitude.value = location.latLng.latitude
//        longitude.value = location.latLng.longitude
//        showToastLiveData.value = "Don't forget your title and description!"

        selectedPOI.postValue(location)
        reminderSelectedLocationStr.postValue(location.name)
        latitude.postValue(location.latLng.latitude)
        longitude.postValue(location.latLng.longitude)
        showToastLiveData.postValue("Don't forget your title and description!")
    }


    fun saveReminder(reminderData: ReminderDataItem) {
        showLoading.value = true
        viewModelScope.launch(dispatcher) {
            dataSource.saveReminder(
                ReminderDTO(
                    reminderData.title,
                    reminderData.description,
                    reminderData.location,
                    reminderData.latitude,
                    reminderData.longitude,
                    reminderData.id
                )
            )

//            showLoading.value = false
//            showToast.value = app.getString(R.string.reminder_saved)
//            navigationCommand.value = NavigationCommand.Back
            showLoading.postValue(false)
            showToastLiveData.postValue("Reminder Saved!")
            navigationCommand.postValue(NavigationCommand.Back)
        }
    }

    /**
     * Validate the entered data and show error to the user if there's any invalid data
     */
    fun validateEnteredData(reminderData: ReminderDataItem): Boolean {
        if (reminderData.title.isNullOrEmpty()) {
            showSnackBarInt.value = R.string.err_enter_title
            return false
        }

        if (reminderData.location.isNullOrEmpty()) {
            showSnackBarInt.value = R.string.err_select_location
            return false
        }
        return true
    }


    fun saveToDataSource(
        title: String,
        description: String,
        location: String,
        latitude: Double,
        longitude: Double,
        id: String
    ) {
        viewModelScope.launch {
            dataSource.saveReminder(
                ReminderDTO(
                    title,
                    description,
                    location,
                    latitude,
                    longitude,
                    id
                )
            )
        }
    }

}