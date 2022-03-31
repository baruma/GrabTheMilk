package com.udacity.project4.locationreminders.geofence

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.utils.sendNotification
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

 class GeofenceTransitionsJobIntentService : JobIntentService(), CoroutineScope, KoinComponent {
    private val repository by inject<ReminderDataSource>()
     val reminderDataSource: ReminderDataSource by inject()


     val TAG = "GeofenceTransitionsJobIntentService"

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    companion object {
        private const val JOB_ID = 573

        //        TODO: call this to start the JobIntentService to handle the geofencing transition events
        fun enqueueWork(context: Context, intent: Intent) {
            Log.d("Screaming", "geofence enque works")
            enqueueWork(
                context,
                GeofenceTransitionsJobIntentService::class.java, JOB_ID,
                intent
            )
        }

    }

    override fun onHandleWork(intent: Intent) {
        //TODO: handle the geofencing transition events and
        // send a notification to the user when he enters the geofence area
        //TODO call @sendNotification
        val geoFenceEvent= GeofencingEvent.fromIntent(intent)
        when (geoFenceEvent.geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                shootNotification(geoFenceEvent.triggeringGeofences)
            }
        }

    }


    //TODO: get the request id of the current geofence
    private fun shootNotification(triggeringGeofences: List<Geofence>) {
        val requestId = when {
            triggeringGeofences.isNotEmpty() ->
            {
                triggeringGeofences[0].requestId
            }
            else -> {
                return
            }
        }

        //Get the local repository instance
        //val remindersLocalRepository: RemindersLocalRepository by inject()

        CoroutineScope(coroutineContext).launch(SupervisorJob()) {
            val result = reminderDataSource.getReminderByID(requestId)
            if (result is Result.Success<ReminderDTO>) {
                val reminderDTO = result.data

                sendNotification(
                    this@GeofenceTransitionsJobIntentService, ReminderDataItem(
                        reminderDTO.title,
                        reminderDTO.description,
                        reminderDTO.location,
                        reminderDTO.latitude,
                        reminderDTO.longitude,
                        reminderDTO.id
                    )
                )

            }
        }
    }

}