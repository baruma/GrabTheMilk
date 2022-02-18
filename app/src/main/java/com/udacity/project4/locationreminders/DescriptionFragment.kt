//package com.udacity.project4.locationreminders
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.udacity.project4.R
//import com.udacity.project4.databinding.ActivityReminderDescriptionBinding
//import com.udacity.project4.databinding.FragmentDescriptionBinding
//import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
//import kotlinx.android.synthetic.main.fragment_description.view.*
//import org.koin.androidx.viewmodel.ext.android.viewModel
//
//class DescriptionFragment : Fragment() {
//    private lateinit var binding: FragmentDescriptionBinding
//    private lateinit var contentView: View
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//            binding =
//                DataBindingUtil.inflate(inflater, R.layout.fragment_description, container, false)
//            binding.lifecycleOwner = this
//
//            contentView = binding.root
//
//            return contentView
//
//    }
//
//    companion object {
//        private const val EXTRA_ReminderDataItem = "EXTRA_ReminderDataItem"
//
//        //        receive the reminder object after the user clicks on the notification
//        fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
//            val intent = Intent(context, ReminderDescriptionActivity::class.java)
//            intent.putExtra(EXTRA_ReminderDataItem, reminderDataItem)
//            return intent
//        }
//    }
//
//
//}