package com.udacity.project4.locationreminders

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.udacity.project4.R
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.geofence.GeofenceBroadcastReceiver
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.locationreminders.savereminder.RemindersViewModel
import kotlinx.android.synthetic.main.activity_reminders.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RemindersActivity : AppCompatActivity() {

    lateinit var geofencingClient: GeofencingClient
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
        val idList = intent.extras?.getStringArray("GEOFENCE_IDS") ?: emptyArray()
        idList.forEach {
            _viewModel.retrieveReminderUponUserEnteringPOI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        geofencingClient = LocationServices.getGeofencingClient(this)

        // Create the observer which updates the UI.
        val geofenceRequestObserver = Observer<GeofencingRequest> { geofenceRequest ->
            addGeofence(geofenceRequest, geofencePendingIntent)
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        _viewModel.geofenceRequest.observe(this, geofenceRequestObserver)

        _viewModel.createGeofenceRequestForExistingPOI()

        val reminderObserver = Observer<ReminderDTO> {

            createNotificationForGeofence(it)
        }

        _viewModel.reminder.observe(this, reminderObserver)



        createNotificationForGeofence(ReminderDTO("blah", "blah", "San Francisco", 222.2, 333.3, "sljflskdfjlksdjflk"))
    }

    fun createNotificationForGeofence(reminder: ReminderDTO) {
        val intent = Intent(this, RemindersActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

//        val intentForDescriptionScreen = Intent(this, DescriptionFragment::class.java)

        val dataBundle = Bundle()
        dataBundle.putSerializable("reminder", reminder)

        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.descriptionFragment)
            .setArguments(dataBundle)
            .createPendingIntent()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ChannelID"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("ChannelID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        var builder = NotificationCompat.Builder(this, "ChannelID")
            .setContentTitle(reminder.title)
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
            .setContentText(reminder.location)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(reminder.description))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }
    }

    fun addGeofence(request: GeofencingRequest, intent: PendingIntent) {
        geofencingClient.addGeofences(request, intent).run {
            addOnSuccessListener {
                Toast.makeText(applicationContext, "Geofence Saved", Toast.LENGTH_SHORT).show()
            }
            addOnFailureListener {
                Toast.makeText(applicationContext, "Geofence Failed to Save", Toast.LENGTH_SHORT).show()
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

    companion object {
        private const val EXTRA_ReminderDataItem = "EXTRA_ReminderDataItem"

        fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
            val intent = Intent(context, DescriptionFragment::class.java)
            intent.putExtra(EXTRA_ReminderDataItem, reminderDataItem)
            return intent
        }
    }
}
