package com.udacity.project4

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem

//class ReminderDescriptionViewModel: ViewModel() {
//    // put reminder in here so you can use it in the viewmodel for databinding
//    val reminder: ReminderDTO = intent.extras!!.getSerializable("reminder") as ReminderDTO
//
//    companion object {
//        private const val EXTRA_ReminderDataItem = "EXTRA_ReminderDataItem"
//
//        fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
//            val intent = Intent(context, ReminderDescriptionActivity::class.java)
//            intent.putExtra(EXTRA_ReminderDataItem, reminderDataItem)
//            return intent
//        }
//    }
//
//}