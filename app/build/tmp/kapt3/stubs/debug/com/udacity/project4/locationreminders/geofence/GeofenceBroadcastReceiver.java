package com.udacity.project4.locationreminders.geofence;

import java.lang.System;

/**
 * Triggered by the Geofence.  Since we can have many Geofences at once, we pull the request
 * ID from the first Geofence, and locate it within the cached data in our Room DB
 *
 * Or users can add the reminders and then close the app, So our app has to run in the background
 * and handle the geofencing in the background.
 * To do that you can use https://developer.android.com/reference/android/support/v4/app/JobIntentService to do that.
 */
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/udacity/project4/locationreminders/geofence/GeofenceBroadcastReceiver;", "Landroid/content/BroadcastReceiver;", "_viewModel", "Lcom/udacity/project4/locationreminders/savereminder/RemindersViewModel;", "(Lcom/udacity/project4/locationreminders/savereminder/RemindersViewModel;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_debug"})
public final class GeofenceBroadcastReceiver extends android.content.BroadcastReceiver {
    private final com.udacity.project4.locationreminders.savereminder.RemindersViewModel _viewModel = null;
    
    public GeofenceBroadcastReceiver(@org.jetbrains.annotations.NotNull()
    com.udacity.project4.locationreminders.savereminder.RemindersViewModel _viewModel) {
        super();
    }
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
    }
}