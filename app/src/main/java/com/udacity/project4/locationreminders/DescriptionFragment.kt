package com.udacity.project4.locationreminders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentDescriptionBinding
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.locationreminders.savereminder.RemindersViewModel
import kotlinx.android.synthetic.main.fragment_description.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DescriptionFragment : Fragment() {

    private lateinit var binding: FragmentDescriptionBinding
    val _viewModel: DescriptionViewModel by viewModel()

    // TODO: Stuck here - how do you get data from pending intent to here.
    private lateinit var contentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_description, container, false)

        val reminder: ReminderDTO = arguments!!.getSerializable("reminder") as ReminderDTO
        _viewModel.reminder = reminder

        binding.viewModel = _viewModel
        return binding.root
    }




}