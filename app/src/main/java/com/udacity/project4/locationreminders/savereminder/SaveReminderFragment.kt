package com.udacity.project4.locationreminders.savereminder

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.BuildConfig
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand.*
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.maps.MapsFragment
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SaveReminderFragment : BaseFragment() {
    override val _viewModel: SaveReminderViewModel by sharedViewModel()
    private val remindersViewModel: RemindersViewModel by sharedViewModel()

    private lateinit var binding: FragmentSaveReminderBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    // The entry point to the Fused Location Provider.

    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    // private var defaultLocation = LatLng(-33.8523341, 151.2106085)
    // private var map: GoogleMap? = null


    private val isRunningQOrLater =
        android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_save_reminder, container, false)

        setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        binding.viewModel = _viewModel

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        // I think it has something to do with the view model.
        binding.selectLocationField.setOnClickListener {
            val isBackgroundLocationGranted = (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)

            val isFineLocationGranted = (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
            Log.d("eh", "Access background location $isBackgroundLocationGranted")
            Log.d("eh", "Access fine location $isFineLocationGranted")


            if (isBackgroundLocationGranted && isFineLocationGranted) {

//            Toast.makeText( requireContext(), "SCREAMING", Toast.LENGTH_LONG).show()
                locationPermissionGranted = true
                foregroundAndBackgroundLocationPermissionApproved()
                _viewModel.navigationCommand.value =
                    To(SaveReminderFragmentDirections.actionSaveReminderFragmentToMapsFragment())
            } else {
                getLocationPermission()
                ActivityCompat.requestPermissions(
                    context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                )
                requestForegroundAndBackgroundLocationPermissions()
            }
        }

        binding.saveReminderFAB.setOnClickListener {
            // create geofence
            saveReminder()
        }

        if (locationPermissionGranted) {
            getLocationPermission()
            statusCheck()
        } else {
            foregroundAndBackgroundLocationPermissionApproved()
        }

        val toastObserver = Observer<String> {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
        _viewModel.showToast.observe(viewLifecycleOwner, toastObserver)
    }

    private fun statusCheck() {
        val manager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            buildAlertMessageNoGps()
            showLocationDialog()
        }
    }

    // app compat supports lower versions of android and has the most bug fixes because google wants to support the older versions.

    private fun showLocationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("The location service is not enabled on the phone.")
            .setMessage("Go to Settings > Location information (enable location service).")
            .setNegativeButton(
                "cancel"
            ) { dialogInterface, i -> }
            .setPositiveButton("Square that Away") { dialog, which ->
                val intent = Intent()
                intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                try {
                    requireContext().startActivity(intent)
                } catch (ex: ActivityNotFoundException) {
                    intent.action = Settings.ACTION_SETTINGS
                    try {
                        requireContext().startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            .show()
    }


    // This is working as it should.  Left off here.  Need to make sure user's Location Service is on.

    // check the ui elements and see whether they're null / empty or not, then proceed with saveReminder and saving a geofence.
    // fab button should be enabled. but if fields aren't poulated, give dialogue or message to user to fill in fields.
    private fun saveReminder() {
        val title = _viewModel.reminderTitle.value ?: ""
        val description = _viewModel.reminderDescription.value ?: ""
        val location = _viewModel.reminderSelectedLocationStr.value
        val latitude = _viewModel.latitude.value
        val longitude = _viewModel.longitude.value

        if (_viewModel.validateEnteredData(ReminderDataItem(title, description, location, latitude, longitude))) {

            _viewModel.saveReminder(
                ReminderDataItem(
                    title,
                    description,
                    location,
                    latitude,
                    longitude
                )
            )

            checkDeviceLocationSettingsAndStartGeofence()
            findNavController().popBackStack()
        }

    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
//            ActivityCompat.requestPermissions(
//                context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
//            )
            requestForegroundAndBackgroundLocationPermissions()
        }
    }

    // If the option "Allow all the time" is not used - shoot a dialogue at the user.
    // This function should be in the "getLocationPermissions" function.
    @TargetApi(29)
    private fun foregroundAndBackgroundLocationPermissionApproved(): Boolean {
        val foregroundLocationApproved = (
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ))
        val backgroundPermissionApproved =
            if (isRunningQOrLater) {
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
            } else {
                Toast.makeText(requireContext(), "NOPE", Toast.LENGTH_LONG).show()
                false
                // Create a dialog telling user the app experience would be better if they gave fore and background permissions.
            }
        return foregroundLocationApproved && backgroundPermissionApproved
    }

    @TargetApi(29)
    private fun requestForegroundAndBackgroundLocationPermissions() {
        if (foregroundAndBackgroundLocationPermissionApproved())
            return
        var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val resultCode = when {
            isRunningQOrLater -> {
                permissionsArray += Manifest.permission.ACCESS_BACKGROUND_LOCATION
                REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE
            }
            else -> REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
        }
        Log.d(TAG, "Request foreground only location permission")
        requestPermissions(
            permissionsArray,
            resultCode
        )
    }

    // TODO: Check if Device's Location is turned on
    // TODO: Allow Geofencing if the above conditions are met

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        val locationResult = fusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener(activity as FragmentActivity) { task ->
            if (task.isSuccessful) {
                // Set the map's camera position to the current location of the device.
                lastKnownLocation = task.result
//                if (lastKnownLocation != null) {
//                    map?.moveCamera(
//                        CameraUpdateFactory.newLatLngZoom(
//                            LatLng(
//                                lastKnownLocation!!.latitude,
//                                lastKnownLocation!!.longitude
//                            ), DEFAULT_ZOOM.toFloat()
//                        )
//                    )
//                }
            } else {
//                Log.d(TAG, "Current location is null. Using defaults.")
//                Log.e(TAG, "Exception: %s", task.exception)
//                map?.moveCamera(
//                    CameraUpdateFactory
//                        .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
//                )
//                map?.uiSettings?.isMyLocationButtonEnabled = false
            }
        }
    }

    fun showLocationPermissionsDialog() {
        // Create an instance of the dialog fragment and show it
//        val dialog = LocationPermissionsDialogFragment()
//        dialog.show()
//        dialog.show(supportFragmentManager, "NoticeDialogFragment")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionResult")

        if (
            grantResults.isEmpty() ||
            grantResults[LOCATION_PERMISSION_INDEX] == PackageManager.PERMISSION_DENIED ||
            (requestCode == REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE &&
                    grantResults[BACKGROUND_LOCATION_PERMISSION_INDEX] ==
                    PackageManager.PERMISSION_DENIED)
        ) {
            Snackbar.make(
                binding.root,
                R.string.permission_denied_explanation,
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(R.string.settings) {
                    startActivity(Intent().apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                }.show()
        } else {
            //Permissions granted
//            getUserLocation()
            checkDeviceLocationSettingsAndStartGeofence()
        }
    }

    // Call this on save button hit
    private fun checkDeviceLocationSettingsAndStartGeofence(resolve: Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(requireContext())
        val locationSettingsResponseTask =
            settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(
                        requireActivity(), REQUEST_TURN_DEVICE_LOCATION_ON
//                        com.udacity.project4.save.REQUEST_TURN_DEVICE_LOCATION_ON
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, "Error getting location settings resolution: " + sendEx.message)
                }
            } else {
//                Snackbar.make(
//                    binding.map,
//                    R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
//                ).setAction(android.R.string.ok) {
//                    checkDeviceLocationSettingsAndStartGeofence()
//                }.show()
            }
        }
        locationSettingsResponseTask.addOnCompleteListener {
            if (it.isSuccessful) {
//                addGeofenceForClue()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _viewModel.onClear()
    }


    // is a singleton.  will not know what context you're using because it has global access to all of them.
    companion object {

        // May need to change the below into an activity
        private val TAG = MapsFragment::class.java.simpleName
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        // Keys for storing activity state.
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

        // Used for selecting the current place.
        private const val M_MAX_ENTRIES = 5

        private fun checkDeviceLocationSettingsAndStartGeofence(
            mapsFragment: MapsFragment,
            resolve: Boolean = true
        ) {
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_LOW_POWER
            }
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

            val settingsClient = LocationServices.getSettingsClient(mapsFragment.requireContext())
            val locationSettingsResponseTask =
                settingsClient.checkLocationSettings(builder.build())

            locationSettingsResponseTask.addOnFailureListener { exception ->
                if (exception is ResolvableApiException && resolve) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        exception.startResolutionForResult(
                            mapsFragment.activity,
                            REQUEST_TURN_DEVICE_LOCATION_ON
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        Log.d(TAG, "Error geting location settings resolution: " + sendEx.message)
                    }
                } else {
//                    Snackbar.make(
//                        mapsFragment.binding.root,
//                        R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
//                    ).setAction(android.R.string.ok) {
////                        checkDeviceLocationSettingsAndStartGeofence()
//                    }.show()
                }
            }

        }


    }
}

//class LocationPermissionsDialogFragment : DialogFragment() {
//
//    fun onCreateDialog(savedInstanceState: Bundle): Dialog {
//        return activity?.let {
//            // Use the Builder class for convenient dialog construction
//            val builder = AlertDialog.Builder(it)
//            builder.setMessage("Allowing location permissions on this app will vastly improve your experience.")
//                .setPositiveButton("Alright",
//                    DialogInterface.OnClickListener { dialog, id ->
//                        // START THE GAME!
//                    })
//            // Create the AlertDialog object and return it
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }
//}

const val REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE = 33
const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
const val TAG = "AuthenticationActivity"
const val LOCATION_PERMISSION_INDEX = 0
const val BACKGROUND_LOCATION_PERMISSION_INDEX = 1

