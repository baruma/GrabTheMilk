package com.udacity.project4.locationreminders.savereminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
import com.google.android.gms.location.GeofencingRequest
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.launch

class RemindersViewModel(
    private val dataSource: ReminderDataSource
) : ViewModel() {

    private val _geofenceRequest = MutableLiveData<GeofencingRequest>()
    val geofenceRequest: LiveData<GeofencingRequest>
        get() = _geofenceRequest

    private val _reminder = MutableLiveData<ReminderDTO>()
    val reminder: LiveData<ReminderDTO>
        get() = _reminder

    private suspend fun fetchCoordinatesFromDB(): List<SimplePOI> {
        val result = dataSource.getReminders()
        val coordinateList = mutableListOf<SimplePOI>()

        when (result) {
            is Result.Success<List<ReminderDTO>> -> {
                result.data.forEach {
                    val coordinate = SimplePOI(it.id, it.latitude!!, it.longitude!!)
                    coordinateList.add(coordinate)
                }
            }
            is Result.Error -> {

            }
        }
        return coordinateList
    }

    fun createGeofenceRequest(latitude: Double, longitude: Double, location: String) {
        viewModelScope.launch {
            val poiToGeofence = Geofence.Builder()
                .setCircularRegion(latitude!!, longitude!!, 100f)
                .setExpirationDuration(Geofence.NEVER_EXPIRE).setRequestId(location)
                .setTransitionTypes(GEOFENCE_TRANSITION_ENTER)
                .build()

            _geofenceRequest.value = GeofencingRequest.Builder().apply {
                setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                addGeofences(listOf(poiToGeofence))
            }.build()
        }
    }

    fun createGeofenceRequestForExistingPOI() {
        viewModelScope.launch {
            val geofences = mutableListOf<Geofence>()
            fetchCoordinatesFromDB().forEach {
                val poiToGeofence =
                    Geofence.Builder().setCircularRegion(it.latitude!!, it.longitude!!, 100f)
                        .setExpirationDuration(
                            Geofence.NEVER_EXPIRE
                        ).setRequestId(it.id)  // put poi name in here
                        .setTransitionTypes(GEOFENCE_TRANSITION_ENTER)
                        .build()
                geofences.add(poiToGeofence)
            }

            if (geofences.isNotEmpty()) {
                _geofenceRequest.value = GeofencingRequest.Builder().apply {
                    setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                    addGeofences(geofences)
                }.build()
            }
        }
    }

    fun retrieveReminderUponUserEnteringPOI(id: String) {
        viewModelScope.launch {
            val result = dataSource.getReminderByID(id)

            when (result) {
                is Result.Success<ReminderDTO> -> {
                    _reminder.value = result.data
                }
                // TODO: handle Result.error
            }
        }
    }

    class SimplePOI(val id: String, val latitude: Double, val longitude: Double)
}