package com.udacity.project4.locationreminders.geofence

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.udacity.project4.authentication.AuthenticationActivity.Companion.TAG
import com.udacity.project4.locationreminders.RemindersActivity
import com.udacity.project4.locationreminders.savereminder.RemindersViewModel

/**
 * Triggered by the Geofence.  Since we can have many Geofences at once, we pull the request
 * ID from the first Geofence, and locate it within the cached data in our Room DB
 *
 * Or users can add the reminders and then close the app, So our app has to run in the background
 * and handle the geofencing in the background.
 * To do that you can use https://developer.android.com/reference/android/support/v4/app/JobIntentService to do that.
 *
 */

// Have viewmodel do what you're trying to do in the coroutine scope
class GeofenceBroadcastReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes
                .getStatusCodeString(geofencingEvent.errorCode)
            Log.e(TAG, errorMessage)
            return
        }

        val geofenceTransition = geofencingEvent.geofenceTransition

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Toast.makeText(context, "Entered Geofence", Toast.LENGTH_SHORT)
            val triggeringGeofences = geofencingEvent.triggeringGeofences
            val listOfIDs = /*mutableListOf<String>()
            triggeringGeofences.forEach {
                listOfIDs.add(it.requestId)
            }*/

                triggeringGeofences.map { it.requestId }


            val intent = Intent(context, RemindersActivity::class.java)
            val bundle = Bundle()

            bundle.putStringArray("GEOFENCE_IDS", listOfIDs.toTypedArray())
            startActivity(context!!, intent, null)

        } else {
            // Log the error.
            Log.e(TAG, "Invalid Transition Type")
        }
    }

}