package com.udacity.project4.locationreminders.savereminder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
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

    val reminderTitle = MutableLiveData<String>()
    val reminderDescription = MutableLiveData<String>()
    var reminderSelectedLocationStr = MutableLiveData<String>()
    val latitude = MutableLiveData<Double>()
    val longitude = MutableLiveData<Double>()
    var selectedPOI = MutableLiveData<PointOfInterest>()

    var poi: PointOfInterest? = null

    fun onClear() {
        reminderTitle.value = null
        reminderDescription.value = null
        reminderSelectedLocationStr.value = null
        selectedPOI.value = null
        latitude.value = null
        longitude.value = null
    }

    fun savePOI(location: PointOfInterest) {
        this.poi = location
        selectedPOI.postValue(location)
        reminderSelectedLocationStr.postValue(location.name)
        latitude.postValue(location.latLng.latitude)
        longitude.postValue(location.latLng.longitude)
        showSnackBar.postValue("Don't forget your title and description!")
    }

    fun saveCustomLocation(coordinates: LatLng) {
        val location = PointOfInterest(coordinates, "someId", "Dropped Pin")
        this.poi = location
        selectedPOI.postValue(location)
        reminderSelectedLocationStr.postValue(customPinName)
        latitude.postValue(coordinates.latitude)
        longitude.postValue(coordinates.longitude)
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

                showLoading.postValue(false)
                showSnackBar.postValue("Reminder Saved!")
                navigationCommand.postValue(NavigationCommand.Back)
            }
    }

    fun validateEnteredData(reminderData: ReminderDataItem): Boolean {
        if (reminderData.title.isNullOrEmpty()) {
            showSnackBarInt.postValue(R.string.err_enter_title)
            return false
        }

        if (reminderData.description.isNullOrEmpty()) {
            showSnackBarInt.postValue(R.string.empty_desription_error)
            return false
        }

        if (reminderData.location.isNullOrEmpty()) {
            showSnackBarInt.postValue(R.string.err_select_location)
                return false
        }
        return true
    }

    companion object {
        val customPinName = "Dropped Pin"
    }
}