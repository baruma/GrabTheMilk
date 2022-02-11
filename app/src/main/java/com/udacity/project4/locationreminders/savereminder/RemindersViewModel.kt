package com.udacity.project4.locationreminders.savereminder

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.udacity.project4.base.BaseViewModel
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.data.local.RemindersDatabase
import com.udacity.project4.locationreminders.geofence.GeofenceBroadcastReceiver
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RemindersViewModel(
    app: Application, private val dataSource: ReminderDataSource
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
                    result.data.forEach{
                        val coordinate = SimplePOI(it.id, it.latitude!!, it.longitude!!)
                        coordinateList.add(coordinate)
                    }
                }

            else -> {
                return emptyList()
            }
        }
        return coordinateList
    }

    // pass in an id here
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

    // Need to learn how to test Geofences and entering them to test this function.
    fun createGeofenceRequestForExistingPOI() {
        viewModelScope.launch {
            var geofences = mutableListOf<Geofence>()

            fetchCoordinatesFromDB().forEach {
                val poiToGeofence =
                    Geofence.Builder().setCircularRegion(it.latitude!!, it.longitude!!, 100f)
                        .setExpirationDuration(
                            Geofence.NEVER_EXPIRE
                        ).setRequestId(it.name)  // put poi name in here
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
            val result = dataSource.getReminder(id)

            when (result) {
                is Result.Success <ReminderDTO> -> {
                    _reminder.value = result.data
                }

                else -> {

                }
            }
        }
    }


    class SimplePOI(val name: String, val latitude: Double, val longitude: Double)
}

// Left off on transitions
