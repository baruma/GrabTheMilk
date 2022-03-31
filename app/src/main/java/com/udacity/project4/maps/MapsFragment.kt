package com.udacity.project4.maps

import android.annotation.SuppressLint
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
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
import com.udacity.project4.BuildConfig
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentMapsBinding
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import org.koin.android.ext.android.inject
import java.util.*


class MapsFragment : Fragment(), GoogleMap.OnPoiClickListener {

    private lateinit var binding: FragmentMapsBinding
    val saveReminderViewModel: SaveReminderViewModel by inject()


    private val TAG = MapsActivity::class.java.simpleName
    private val KEY_CAMERA_POSITION = "camera_position"

    private val runningQOrLater =
        android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q
    private var gmapKey: String = BuildConfig.GOOGLE_MAP_KEY

    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null

    // The entry point to the Places API.
    private lateinit var placesClient: PlacesClient

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private var defaultLocation = LatLng(-33.8523341, 151.2106085)

    private var lastKnownLocation: Location? = null

    // This needs to be MutableDataSource
    private var pointOfInterest: PointOfInterest? = null


    // We were working on Mutable Live Data between fragments and ViewModels
    override fun onPoiClick(poi: PointOfInterest) {
        Toast.makeText(context, """Clicked: ${poi.name}
            Place ID:${poi.placeId}
            Latitude:${poi.latLng.latitude}
            Longitude:${poi.latLng.longitude}""",
            Toast.LENGTH_LONG
        ).show()

        pointOfInterest = poi

        map?.addMarker(
            MarkerOptions()
                .position(pointOfInterest!!.latLng)
        )

        saveReminderViewModel.savePOI(poi)

        //navigate back
        findNavController().popBackStack()
    }

    private val callback = OnMapReadyCallback { googleMap ->
        this.map = googleMap  // init happens 1x and only 1x.
        setMapStyle(googleMap)

        if (lastKnownLocation != null) {
            map?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        lastKnownLocation!!.latitude,
                        lastKnownLocation!!.longitude
                    ), 15.0f
                )
            )
        }

        googleMap.setOnPoiClickListener(this)

        googleMap.setOnMapLongClickListener { latLng ->
            // googleMap.clear()

            // A Snippet is Additional text that's displayed below the title.
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
                    .position(latLng))

            saveReminderViewModel.saveCustomLocation(latLng)
            findNavController().popBackStack()
        }

      //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation))
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

        Toast.makeText(this@MapsFragment.requireActivity(), "Please choose a place for your reminder.", Toast.LENGTH_SHORT).show()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getUserLocation()


        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }


        // Construct a PlacesClient
        Places.initialize(requireContext(), gmapKey)
        placesClient = Places.createClient(requireContext())

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.normal_map -> {
            map!!.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map!!.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map!!.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
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
                // Set the map's camera position to the current location of the device.
                lastKnownLocation = task.result

                Log.d("SCREAMING", lastKnownLocation.toString())
                if (lastKnownLocation != null) {
                    map?.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                lastKnownLocation!!.latitude,
                                lastKnownLocation!!.longitude
                            ), 12.0f
                        )
                    )
                }
            } else {
                Log.d(TAG, "Current location is null. Using defaults.")
                Log.e(TAG, "Exception: %s", task.exception)
                map?.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(defaultLocation, 12.0f)
                )
                map?.uiSettings?.isMyLocationButtonEnabled = false
            }
        }
    }
}