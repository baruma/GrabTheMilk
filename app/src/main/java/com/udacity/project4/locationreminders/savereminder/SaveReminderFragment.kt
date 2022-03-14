package com.udacity.project4.locationreminders.savereminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.PointOfInterest
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import kotlinx.android.synthetic.main.fragment_save_reminder.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class SaveReminderFragment : BaseFragment() {
    override val _viewModel: SaveReminderViewModel by sharedViewModel()
    private val remindersViewModel: RemindersViewModel by sharedViewModel()
    private lateinit var binding: FragmentSaveReminderBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_save_reminder, container, false)

        setDisplayHomeAsUpEnabled(true)

        binding.viewModel = _viewModel
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.selectLocation.setOnClickListener {
            _viewModel.navigationCommand.value =
                NavigationCommand.To(SaveReminderFragmentDirections.actionSaveReminderFragmentToMapsFragment())
        }

        binding.saveReminder.isEnabled =
            !(_viewModel.reminderTitle.value == null || _viewModel.reminderDescription.value == null|| _viewModel.location == null)

        binding.saveReminder.setOnClickListener {


            val title = _viewModel.reminderTitle.value
            val description = _viewModel.reminderDescription.value
            val location = _viewModel.reminderSelectedLocationStr.value
            val latitude = _viewModel.latitude.value
            val longitude = _viewModel.longitude.value


            remindersViewModel.createGeofenceRequest(latitude!!, longitude!!, location!!)

            _viewModel.saveToDataSource(
                title!!,
                description!!,
                location,
                latitude,
                longitude,
                UUID.randomUUID().toString())
            findNavController().popBackStack()
        }

            val selectedPOIObserver = Observer<PointOfInterest> {
                saveReminder.isEnabled = true
            }

        _viewModel.selectedPOI.observe(viewLifecycleOwner, selectedPOIObserver)

        val toastObserver = Observer<String> {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
        _viewModel.showToast.observe(viewLifecycleOwner, toastObserver)
    }


    override fun onDestroy() {
        super.onDestroy()
        _viewModel.onClear()
    }
}
