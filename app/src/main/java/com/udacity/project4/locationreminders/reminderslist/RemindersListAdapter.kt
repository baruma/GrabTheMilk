package com.udacity.project4.locationreminders.reminderslist

import com.udacity.project4.R
import com.udacity.project4.base.BaseRecyclerViewAdapter


// Okay so I might need help with this afterall.
class RemindersListAdapter(callBack: (selectedReminder: ReminderDataItem) -> Unit) :
    BaseRecyclerViewAdapter<ReminderDataItem>(callBack) {
    override fun getLayoutRes(viewType: Int) = R.layout.it_reminder
}