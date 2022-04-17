package com.udacity.project4.locationreminders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentDescriptionBinding
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import org.koin.androidx.viewmodel.ext.android.viewModel

interface OnReminderItemSelectListener {
    fun onReminderItemSelectListener(reminder: ReminderDataItem)
}

class DescriptionFragment : Fragment() {
    companion object {
        val TAG = DescriptionFragment::class.java.simpleName

        private const val EXTRA_ReminderDataItem = "EXTRA_ReminderDataItem"

        fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
            val intent = Intent(context, DescriptionFragment::class.java)
            intent.putExtra(EXTRA_ReminderDataItem, reminderDataItem)
            return intent
        }

    }

    private lateinit var binding: FragmentDescriptionBinding
    val _viewModel: DescriptionViewModel by viewModel()

    private lateinit var contentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_description, container, false)

        //val reminder: ReminderDTO = requireArguments().getSerializable("reminder") as ReminderDTO
        val reminder: ReminderDataItem = requireArguments().getSerializable("reminder") as ReminderDataItem
        _viewModel.reminder = reminder

        binding.viewModel = _viewModel
        return binding.root
    }

}