package com.udacity.project4.maps

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.BuildConfig
import com.udacity.project4.R
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentMapsBinding
import com.udacity.project4.locationreminders.savereminder.SaveReminderFragment
import com.udacity.project4.locationreminders.savereminder.SaveReminderFragmentDirections
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*


class MapsFragment : Fragment(), GoogleMap.OnPoiClickListener {

    private lateinit var binding: FragmentMapsBinding
    val saveReminderViewModel: SaveReminderViewModel by inject()

    private val TAG = MapsFragment::class.java.simpleName
    private val KEY_CAMERA_POSITION = "camera_position"

    private var gmapKey: String = BuildConfig.GOOGLE_MAP_KEY

    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null
    private lateinit var placesClient: PlacesClient

    val _viewModel: SaveReminderViewModel by sharedViewModel()

    private val zoom = 15.0f

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    private var defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var lastKnownLocation: Location? = null
    private var pointOfInterest: PointOfInterest? = null

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var locationPermissionGranted = false
    private val isRunningROrLater =
        android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R

    override fun onPoiClick(poi: PointOfInterest) {
        Snackbar.make(
            requireView(), """Clicked: ${poi.name}
            Place ID:${poi.placeId}
            Latitude:${poi.latLng.latitude}
            Longitude:${poi.latLng.longitude}""",
            Snackbar.LENGTH_LONG
        ).show()

        pointOfInterest = poi

        map?.addMarker(
            MarkerOptions()
                .position(pointOfInterest!!.latLng)
        )

        saveReminderViewModel.savePOI(poi)
        findNavController().popBackStack()
    }

    private val callback = OnMapReadyCallback { googleMap ->
        this.map = googleMap  // init happens 1x and only 1x.
        setMapStyle(googleMap)
        locateUser()

        if (lastKnownLocation != null) {
            map?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        lastKnownLocation!!.latitude,
                        lastKnownLocation!!.longitude
                    ), zoom
                )
            )
        }

        googleMap.setOnPoiClickListener(this)
        googleMap.setOnMapLongClickListener { latLng ->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )

            googleMap.addMarker(
                MarkerOptions()
                    .snippet(snippet)
                    .title("Custom Location")
                    .position(latLng)
            )

            saveReminderViewModel.saveCustomLocation(latLng)
            findNavController().popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        getUserLocation()

        if (savedInstanceState != null) {
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

        Places.initialize(requireContext(), gmapKey)
        placesClient = Places.createClient(requireContext())

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        Snackbar.make(
            binding.root,
            "Please choose a place for your reminder.",
            Snackbar.LENGTH_LONG
        ).show()

//        statusCheck()
        /*
            Make sure GPS is on the device
            isFineLocationGranted || isCoarseLocationGranted

         */


        // FOREGROUND VARIABLES
        val isFineLocationGranted = (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)

        val isCoarseLocationGranted = (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)

        if (isFineLocationGranted || isCoarseLocationGranted) {
            val manager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var isGPSOn = manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (isGPSOn) {
                // great we have everything
                // show blue dot, zoom in on user
                getUserLocation()
            } else {
                showLocationDialog()
            }
        } else {
            // ask for fine location
            var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            val resultCode = SaveReminderFragment.REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
            Log.d(MapsFragment.TAG, "Request foreground only location permission")
            requestPermissions(
                permissionsArray,
                resultCode
            )
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.normal_map -> {
            map?.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map?.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map?.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.d(MapsFragment.TAG, "onRequestPermissionResult")
        // permissions
        if (
            requestCode != SaveReminderFragment.REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE ||
            grantResults.isEmpty() ||
            grantResults.first() == PackageManager.PERMISSION_DENIED
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
            // location granted
            // maybe show user location with blue dot
            val manager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var isGPSOn = manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (isGPSOn) {
                // great we have everything
                // show blue dot, zoom in on user
                getUserLocation()
            } else {
                showLocationDialog()
            }

        }
    }
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        Log.d(MapsFragment.TAG, "onRequestPermissionResult")
//        if (
//            grantResults.isEmpty() ||
//            grantResults[SaveReminderFragment.LOCATION_PERMISSION_INDEX] == PackageManager.PERMISSION_DENIED ||
//            (requestCode == SaveReminderFragment.REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE &&
//                    grantResults[SaveReminderFragment.BACKGROUND_LOCATION_PERMISSION_INDEX] ==
//                    PackageManager.PERMISSION_DENIED)
//        ) {
//            Snackbar.make(
//                binding.root,
//                R.string.permission_denied_explanation,
//                Snackbar.LENGTH_INDEFINITE
//            )
//                .setAction(R.string.settings) {
//                    startActivity(Intent().apply {
//                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                        data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
//                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    })
//                }.show()
//        }
//    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            requestForegroundAndBackgroundLocationPermissions()
        }
    }

    @TargetApi(29)
    private fun foregroundAndBackgroundLocationPermissionApproved(): Boolean {
        val foregroundLocationApproved = (
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ))
        val backgroundPermissionApproved =
            if (isRunningROrLater) {
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
            } else {
                Snackbar.make(
                    requireView(),
                    "Background location permission has not been granted",
                    Snackbar.LENGTH_LONG
                ).show()
                // you are here rn
                _viewModel.navigationCommand.value =
                    NavigationCommand.To(SaveReminderFragmentDirections.actionSaveReminderFragmentToMapsFragment())
                false
            }
        return foregroundLocationApproved && backgroundPermissionApproved
    }

    @TargetApi(29)
    private fun requestForegroundAndBackgroundLocationPermissions() {
        if (foregroundAndBackgroundLocationPermissionApproved())
            return
        var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val resultCode = when {
            isRunningROrLater -> {
                permissionsArray += Manifest.permission.ACCESS_BACKGROUND_LOCATION
                MapsFragment.REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE
            }
            else -> MapsFragment.REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
        }
        Log.d(MapsFragment.TAG, "Request foreground only location permission")
        requestPermissions(
            permissionsArray,
            resultCode
        )
    }

    @SuppressLint("MissingPermission")
    private fun locateUser() {
        val isFineLocationGranted = (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)

        val isCourseLocationGranted = (ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        )
                == PackageManager.PERMISSION_GRANTED
                )

        if (isFineLocationGranted || isCourseLocationGranted) {
            map?.isMyLocationEnabled = true
        }
    }

    private fun setMapStyle(map: GoogleMap) {
        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context,
                    R.raw.map_style
                )
            )

            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        fusedLocationProviderClient
        val locationResult = fusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                lastKnownLocation = task.result

                Log.d("SCREAMING", lastKnownLocation.toString())

                lastKnownLocation?.run {
                    map?.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                latitude,
                                longitude
                            ), zoom
                        )
                    )
                }
            } else {
                Log.d(TAG, "Current location is null. Using defaults.")
                Log.e(TAG, "Exception: %s", task.exception)
                map?.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(defaultLocation, zoom)
                )
                map?.uiSettings?.isMyLocationButtonEnabled = false
            }
        }
    }

    private fun statusCheck() {
        val manager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showLocationDialog()
        }
    }

    private fun showLocationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("The location service is not enabled on the phone.")
            .setMessage("Go to Settings > Location information (enable location service).")
            .setNegativeButton(
                "cancel"
            ) { _, _ -> }
            .setPositiveButton("Square that Away") { _, _ ->
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

    override fun onResume() {
        super.onResume()
        val manager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var isGPSOn = manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(isGPSOn) {
            getUserLocation()
        }
    }

    companion object {
        private val TAG = MapsFragment::class.java.simpleName
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        const val REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE = 33
        const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
        const val LOCATION_PERMISSION_INDEX = 0
        const val BACKGROUND_LOCATION_PERMISSION_INDEX = 1
    }
}