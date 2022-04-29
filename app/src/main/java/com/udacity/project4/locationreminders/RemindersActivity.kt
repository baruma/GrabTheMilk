package com.udacity.project4.locationreminders

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.R
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.geofence.GeofenceBroadcastReceiver
import com.udacity.project4.locationreminders.savereminder.RemindersViewModel
import kotlinx.android.synthetic.main.activity_reminders.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class RemindersActivity : AppCompatActivity() {

    private lateinit var geofencingClient: GeofencingClient
    val _viewModel: RemindersViewModel by viewModel()
    private var defaultLocation = LatLng(-33.8523341, 151.2106085)
    var geofenceList = mutableListOf<Geofence>()


    // This activity is listening.  Cannot add jack unless something is listening for it.
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Navigation.findNavController(this, R.id.nav_host_fragment).handleDeepLink(intent)

        val idList = intent.extras?.getStringArray("GEOFENCE_IDS") ?: emptyArray()
        idList.forEach {
            _viewModel.retrieveReminderUponUserEnteringPOI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        geofencingClient = LocationServices.getGeofencingClient(this)

        val geofenceRequestObserver = Observer<GeofencingRequest> { geofenceRequest ->
            addGeofence(geofenceRequest, geofencePendingIntent)
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        _viewModel.geofenceRequest.observe(this, geofenceRequestObserver)

        _viewModel.createGeofenceRequestForExistingPOI()

        val reminderObserver = Observer<ReminderDTO> {

      //      createNotificationForGeofence(it)
        }

        _viewModel.reminder.observe(this, reminderObserver)

//        Toast.makeText(applicationContext, "Oh heyyy", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    fun addGeofence(request: GeofencingRequest, intent: PendingIntent) {

        geofencingClient.addGeofences(request, intent).run {
            addOnSuccessListener {
               // Toast.makeText(applicationContext, "Geofence Saved", Toast.LENGTH_SHORT).show()
                Snackbar.make(findViewById<View>(android.R.id.content).getRootView(), "Geofence Saved", Snackbar.LENGTH_LONG).show()

            }
            addOnFailureListener {
//                Toast.makeText(applicationContext, "Geofence Failed to Save", Toast.LENGTH_SHORT).show()
                Snackbar.make(findViewById<View>(android.R.id.content).getRootView(), "Geofence Failed to Save", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onRequestPermissionsResult (
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

//    companion object {
//        private const val EXTRA_ReminderDataItem = "EXTRA_ReminderDataItem"
//
//        fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
//            val intent = Intent(context, DescriptionFragment::class.java)
//            intent.putExtra(EXTRA_ReminderDataItem, reminderDataItem)
//            return intent
//        }
//    }
}
