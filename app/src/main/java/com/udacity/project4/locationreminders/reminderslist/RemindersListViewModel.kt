package com.udacity.project4.locationreminders.reminderslist

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.project4.R
import com.udacity.project4.base.BaseViewModel
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.launch

class RemindersListViewModel(app: Application,
    private val dataSource: ReminderDataSource
) : BaseViewModel(app) {
    val remindersList = MutableLiveData<List<ReminderDataItem>>()

    private val resources: Resources = app.resources

    fun loadReminders() {
        showLoading.postValue(true)
        viewModelScope.launch {
            val result = dataSource.getReminders()
            showLoading.postValue(false)
            when (result) {
                is Result.Success<List<ReminderDTO>> -> {
                    val dataList = ArrayList<ReminderDataItem>()
                    dataList.addAll(result.data.map { reminder ->
                        ReminderDataItem(
                            reminder.title,
                            reminder.description,
                            reminder.location,
                            reminder.latitude,
                            reminder.longitude,
                            reminder.id
                        )
                    })
                    remindersList.postValue(dataList)

                    if (dataList.isEmpty()) {
                        showToast.postValue(resources.getString(R.string.empty_reminder_message))
                    }
                }
                is Result.Error -> {
                    showErrorMessage.postValue(resources.getString(R.string.reminder_list_empty_error_message))
                }
            }

        }
    }
}