package com.udacity.project4.maps
import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.BuildConfig
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentMapsBinding

class MapsFragment : Fragment(), GoogleMap.OnPoiClickListener {

    private lateinit var binding: FragmentMapsBinding
    private val runningQOrLater =
        android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q
    var gmapKey: String = BuildConfig.GOOGLE_MAP_KEY

    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null

    // The entry point to the Places API.
    private lateinit var placesClient: PlacesClient

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    private var lastKnownLocation: Location? = null

    private var pointOfInterest: LatLng? = null

    override fun onPoiClick(poi: PointOfInterest) {
        Toast.makeText(context, """Clicked: ${poi.name}
            Place ID:${poi.placeId}
            Latitude:${poi.latLng.latitude} Longitude:${poi.latLng.longitude}""",
            Toast.LENGTH_LONG
        ).show()

        pointOfInterest = poi.latLng
    }

    private val callback = OnMapReadyCallback { googleMap ->
        this.map = googleMap
        googleMap.setOnPoiClickListener(this)

        googleMap.addMarker(
            MarkerOptions()
                .position(pointOfInterest!!)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(pointOfInterest))

        if (foregroundAndBackgroundLocationPermissionApproved()) {
            getUserLocation()
        } else {
            requestForegroundAndBackgroundLocationPermissions()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)


        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

        // Construct a PlacesClient
        Places.initialize(context!!, gmapKey)
        placesClient = Places.createClient(context!!)

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context!!)

        // Build the map.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapsFragment) as SupportMapFragment?
//        mapFragment?.getMapAsync(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)
        return binding.root

    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // TODO: Change the map type based on the user's selection.
        R.id.normal_map -> {
            true
        }
        R.id.hybrid_map -> {
            true
        }
        R.id.satellite_map -> {
            true
        }
        R.id.terrain_map -> {
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
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
            getUserLocation()
            checkDeviceLocationSettingsAndStartGeofence()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        val locationResult = fusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener(activity as FragmentActivity) { task ->
            if (task.isSuccessful) {
                // Set the map's camera position to the current location of the device.
                lastKnownLocation = task.result
                if (lastKnownLocation != null) {
                    map?.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                lastKnownLocation!!.latitude,
                                lastKnownLocation!!.longitude
                            ), DEFAULT_ZOOM.toFloat()
                        )
                    )
                }
            } else {
                Log.d(TAG, "Current location is null. Using defaults.")
                Log.e(TAG, "Exception: %s", task.exception)
                map?.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                )
                map?.uiSettings?.isMyLocationButtonEnabled = false
            }
        }
    }

    @TargetApi(29)
    private fun foregroundAndBackgroundLocationPermissionApproved(): Boolean {
        val foregroundLocationApproved = (
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            context!!,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ))
        val backgroundPermissionApproved =
            if (runningQOrLater) {
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            context!!, Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
            } else {
                true
            }
        return foregroundLocationApproved && backgroundPermissionApproved
    }

    @TargetApi(29)
    private fun requestForegroundAndBackgroundLocationPermissions() {
        if (foregroundAndBackgroundLocationPermissionApproved())
            return
        var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val resultCode = when {
            runningQOrLater -> {
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

    private fun checkDeviceLocationSettingsAndStartGeofence(resolve: Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(context!!)
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
                        context!! as Activity?,
                        REQUEST_TURN_DEVICE_LOCATION_ON
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, "Error geting location settings resolution: " + sendEx.message)
                }
            } else {
                Snackbar.make(
                    binding.map,
                    R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    checkDeviceLocationSettingsAndStartGeofence()
                }.show()
            }
        }
        locationSettingsResponseTask.addOnCompleteListener {
            if (it.isSuccessful) {
//                addGeofenceForClue()
            }
        }
    }

    companion object {

//        private val TAG = MapsActivityCurrentPlace::class.java.simpleName

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

            val settingsClient = LocationServices.getSettingsClient(mapsFragment.context!!)
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
                    Snackbar.make(
                        mapsFragment.binding.root,
                        R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                    ).setAction(android.R.string.ok) {
//                        checkDeviceLocationSettingsAndStartGeofence()
                    }.show()
                }
            }

        }
    }

//    override fun onMapReady(map: GoogleMap) {
//
//    }

    private fun getLocationPermission() {

        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

}

private const val REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE = 33
private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
private const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
private const val TAG = "AuthenticationActivity"
private const val LOCATION_PERMISSION_INDEX = 0
private const val BACKGROUND_LOCATION_PERMISSION_INDEX = 1
