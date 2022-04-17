package com.udacity.project4.locationreminders.reminderslist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.R
import com.udacity.project4.authentication.AuthenticationActivity
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentRemindersBinding
import com.udacity.project4.locationreminders.OnReminderItemSelectListener
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import com.udacity.project4.utils.setTitle
import com.udacity.project4.utils.setup
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReminderListFragment : BaseFragment(), OnReminderItemSelectListener {

    override val _viewModel: RemindersListViewModel by viewModel()
    private lateinit var binding: FragmentRemindersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_reminders, container, false
            )
        binding.viewModel = _viewModel

        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(false)
        setTitle(getString(R.string.app_name))

        binding.refreshLayout.setOnRefreshListener { _viewModel.loadReminders() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        setupRecyclerView()
        binding.addReminderFAB.setOnClickListener {
            navigateToAddReminder()
        }

        val snackBarErrorObserver = Observer<String> {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }

        _viewModel.showErrorMessage.observe(viewLifecycleOwner, snackBarErrorObserver)

        val showToastObserver = Observer<String> { text ->
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
        _viewModel.showToast.observe(viewLifecycleOwner, showToastObserver)
    }

    override fun onResume() {
        super.onResume()
        _viewModel.loadReminders()
    }

    private fun navigateToAddReminder() {
        _viewModel.navigationCommand.postValue(
            NavigationCommand.To(ReminderListFragmentDirections.toSaveReminder())
        )
    }

    private fun navigateToDescription(reminder: ReminderDataItem) {
        _viewModel.navigationCommand.postValue(
            NavigationCommand.To(
                ReminderListFragmentDirections.actionReminderListFragmentToDescriptionFragment(reminder)
            )
        )
    }


    private fun setupRecyclerView() {
        val adapter = RemindersListAdapter {
            Log.d("EGGS",  it.title.toString())
            // NAVIGATION CODE HERE
            onReminderItemSelectListener(it)

//            val bundle = Bundle()
//            bundle.putSerializable("reminder", it)
//            childFragmentManager.beginTransaction()
//                .add(DescriptionFragment::class.java, bundle, DescriptionFragment.TAG)
//                .commit()
        }

        binding.reminderssRecyclerView.setup(adapter)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                AuthUI.getInstance()
                    .signOut(requireContext())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val activity = requireActivity()
                            startActivity(Intent(activity, AuthenticationActivity::class.java))
                            activity.finish()
                        } else {
                            //Toast.makeText(context, "Could not logout. You are trapped here.", Toast.LENGTH_SHORT).show()
                            Snackbar.make(requireView(), "Could not log out.  You are trapped here.", Snackbar.LENGTH_LONG).show()
                        }
                    }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onReminderItemSelectListener(reminder: ReminderDataItem) {
        navigateToDescription(reminder)
    }
}