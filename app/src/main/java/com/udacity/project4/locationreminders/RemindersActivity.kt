package com.udacity.project4.locationreminders

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.udacity.project4.R
import com.udacity.project4.locationreminders.geofence.GeofenceBroadcastReceiver
import com.udacity.project4.locationreminders.savereminder.RemindersViewModel
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import kotlinx.android.synthetic.main.activity_reminders.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RemindersActivity : AppCompatActivity() {

    lateinit var geofencingClient: GeofencingClient
    val _viewModel: RemindersViewModel by inject()
    private var defaultLocation = LatLng(-33.8523341, 151.2106085)
    var geofenceList = mutableListOf<Geofence>()

    // This activity is listening.  Cannot add jack unless something is listening for it.
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        geofencingClient = LocationServices.getGeofencingClient(this)

        // Create the observer which updates the UI.
        val geofenceRequestObserver = Observer<GeofencingRequest> { geofenceRequest ->
//            nameTextView.text = newName
            addGeofence(geofenceRequest, geofencePendingIntent)
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        _viewModel.geofenceRequest.observe(this, geofenceRequestObserver)

        _viewModel.createGeofenceRequestForExistingPOI()

    }

    fun addGeofence(request: GeofencingRequest, intent: PendingIntent) {
        geofencingClient.addGeofences(request, intent).run {
            addOnSuccessListener {
                // Geofences added
                // ...
            }
            addOnFailureListener {
                // Failed to add geofences
                // ...
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                (nav_host_fragment as NavHostFragment).navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
