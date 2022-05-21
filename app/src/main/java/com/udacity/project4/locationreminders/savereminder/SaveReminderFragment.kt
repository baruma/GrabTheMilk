package com.udacity.project4.locationreminders.savereminder

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
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
    private var locationPermissionGranted = false
    private var reminderToSave: ReminderDataItem? = null

    private val isRunningROrLater =
        android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        binding.selectLocationField.setOnClickListener {
            _viewModel.navigationCommand.value =
                NavigationCommand.To(SaveReminderFragmentDirections.actionSaveReminderFragmentToMapsFragment())
        }

        binding.saveReminderFAB.setOnClickListener {
            saveReminder()
        }

        if (locationPermissionGranted) {
            getLocationPermission()
            statusCheck()
        } else {
            foregroundAndBackgroundLocationPermissionApproved()
        }

        val toastObserver = Observer<String> {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }
        _viewModel.showSnackBar.observe(viewLifecycleOwner, toastObserver)
    }


    //        // BACKGROUND VARIABLE
//        val isBackgroundLocationGranted = (ContextCompat.checkSelfPermission(
//            requireContext(),
//            Manifest.permission.ACCESS_BACKGROUND_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED)
//
//
//            // FLOW
//            if (isBackgroundLocationGranted|| (isFineLocationGranted || isCoarseLocationGranted)) {
//                locationPermissionGranted = true
//                foregroundAndBackgroundLocationPermissionApproved()
//                _viewModel.navigationCommand.value =
//                    NavigationCommand.To(SaveReminderFragmentDirections.actionSaveReminderFragmentToMapsFragment())
////                statusCheck()
//            } else if (isBackgroundLocationGranted) {
//                statusCheck()
//                _viewModel.navigationCommand.value =
//                    NavigationCommand.To(SaveReminderFragmentDirections.actionSaveReminderFragmentToMapsFragment())
//            } else {
//                if (isRunningROrLater) {
//                    requestForegroundAndBackgroundLocationPermissions()
//                } else {
//                    // Show permission dialog if first time otherwise, bring them to settings
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(
//                            requireActivity(),
//                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                        )
//                    ) {
//                        // if first time requesting show permission dialog
//                        // if after first time, show snackbar with settings button
//                        var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
//                        val resultCode = when {
//                            isRunningROrLater -> {
//                                permissionsArray += Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                                SaveReminderFragment.REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE
//                            }
//                            else -> SaveReminderFragment.REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
//                        }
//                        Log.d(MapsFragment.TAG, "Request foreground only location permission")
//                        requestPermissions(
//                            permissionsArray,
//                            resultCode
//                        )
//
//                        _viewModel.navigationCommand.value =
//                            NavigationCommand.To(SaveReminderFragmentDirections.actionSaveReminderFragmentToMapsFragment())
//
//                    } else {
//                        Snackbar.make(
//                            binding.root,
//                            R.string.permission_denied_explanation,
//                            Snackbar.LENGTH_INDEFINITE
//                        )
//                            .setAction(R.string.settings) {
//                                startActivity(Intent().apply {
//                                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                                    data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
//                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                                })
//                            }.show()
//
//                        showLocationDialog()
//                    }
//                }
//            }
//
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

    private fun saveReminder() {
        val title = _viewModel.reminderTitle.value ?: ""
        val description = _viewModel.reminderDescription.value ?: ""
        val location = _viewModel.reminderSelectedLocationStr.value
        val latitude = _viewModel.latitude.value
        val longitude = _viewModel.longitude.value
        val reminderDTO = ReminderDataItem(
            title,
            description,
            location,
            latitude,
            longitude
        )

        if (_viewModel.validateEnteredData(reminderDTO) && foregroundAndBackgroundLocationPermissionApproved()) {
            saveToDatabase(reminderDTO)
            addGeofence(reminderDTO.latitude!!, reminderDTO.longitude!!, reminderDTO.id)
            findNavController().popBackStack()
        } else {
            reminderToSave = reminderDTO
            requestForegroundAndBackgroundLocationPermissions()
        }
    }

    private fun saveToDatabase(reminder: ReminderDataItem) {
        _viewModel.saveReminder(reminder)
    }

    private fun addGeofence(latitude: Double, longitude: Double, id: String) {
        remindersViewModel.createGeofenceRequest(
            latitude,
            longitude,
            id
        )
    }


    private fun checkForLocationAllTheTimeOrNah() {
        /* Check if we have "Allow access all the time" with location permissions
         if not, ask for it.
         if Granted
             saveReminder to database
             add geofence to geofence manager
           if not granted
              save to database
        */

        if (foregroundAndBackgroundLocationPermissionApproved()) {
            //save to db
            // add geofence
        } else {
            requestForegroundAndBackgroundLocationPermissions()
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
//                Snackbar.make(
//                    requireView(),
//                    "Background location permission has not been granted",
//                    Snackbar.LENGTH_LONG
//                ).show()
                Log.d("snackbar", "foregroundAndBackgroundLocationPermissionApproved is upset")
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionResult")

        if (grantResults.isEmpty() ||
            (requestCode == REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE &&
                    grantResults[BACKGROUND_LOCATION_PERMISSION_INDEX] ==
                    PackageManager.PERMISSION_DENIED)
        ) {
            reminderToSave?.let { reminder ->
                if (reminder.latitude == null || reminder.longitude ==  null) {
                    Log.d(TAG, "Lat and/or Long is null")
                } else {
                    saveToDatabase(reminder)
                    findNavController().popBackStack()
                }
            }
        } else {
            reminderToSave?.let { reminder ->
                if (reminder.latitude == null || reminder.longitude ==  null) {
                    Log.d(TAG, "Lat and/or Long is null")
                } else {
                    saveToDatabase(reminder)
                    addGeofence(reminder.latitude!!, reminder.longitude!!, reminder.id)
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewModel.onClear()
    }

    companion object {
        private val TAG = SaveReminderFragment::class.java.simpleName
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 123587

        const val REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE = 312343
        const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 39824
        const val LOCATION_PERMISSION_INDEX = 0
        const val BACKGROUND_LOCATION_PERMISSION_INDEX = 1
    }
}