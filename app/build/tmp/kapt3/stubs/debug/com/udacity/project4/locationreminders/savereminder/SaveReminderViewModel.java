package com.udacity.project4.locationreminders.savereminder;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010$\u001a\u00020%J\u000e\u0010&\u001a\u00020%2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\'\u001a\u00020%2\u0006\u0010(\u001a\u00020)J\u000e\u0010*\u001a\u00020%2\u0006\u0010(\u001a\u00020)J\u000e\u0010+\u001a\u00020,2\u0006\u0010(\u001a\u00020)R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000fR \u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00190\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u000f\"\u0004\b\u001d\u0010\u001eR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00190\f\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u000fR \u0010!\u001a\b\u0012\u0004\u0012\u00020\u00110\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u000f\"\u0004\b#\u0010\u001e\u00a8\u0006-"}, d2 = {"Lcom/udacity/project4/locationreminders/savereminder/SaveReminderViewModel;", "Lcom/udacity/project4/base/BaseViewModel;", "app", "Landroid/app/Application;", "dataSource", "Lcom/udacity/project4/locationreminders/data/ReminderDataSource;", "(Landroid/app/Application;Lcom/udacity/project4/locationreminders/data/ReminderDataSource;)V", "getApp", "()Landroid/app/Application;", "getDataSource", "()Lcom/udacity/project4/locationreminders/data/ReminderDataSource;", "latitude", "Landroidx/lifecycle/MutableLiveData;", "", "getLatitude", "()Landroidx/lifecycle/MutableLiveData;", "location", "Lcom/google/android/gms/maps/model/PointOfInterest;", "getLocation", "()Lcom/google/android/gms/maps/model/PointOfInterest;", "setLocation", "(Lcom/google/android/gms/maps/model/PointOfInterest;)V", "longitude", "getLongitude", "reminderDescription", "", "getReminderDescription", "reminderSelectedLocationStr", "getReminderSelectedLocationStr", "setReminderSelectedLocationStr", "(Landroidx/lifecycle/MutableLiveData;)V", "reminderTitle", "getReminderTitle", "selectedPOI", "getSelectedPOI", "setSelectedPOI", "onClear", "", "saveLocation", "saveReminder", "reminderData", "Lcom/udacity/project4/locationreminders/reminderslist/ReminderDataItem;", "validateAndSaveReminder", "validateEnteredData", "", "app_debug"})
public final class SaveReminderViewModel extends com.udacity.project4.base.BaseViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.app.Application app = null;
    @org.jetbrains.annotations.NotNull()
    private final com.udacity.project4.locationreminders.data.ReminderDataSource dataSource = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> reminderTitle = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> reminderDescription = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> reminderSelectedLocationStr;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Double> latitude = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Double> longitude = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<com.google.android.gms.maps.model.PointOfInterest> selectedPOI;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.model.PointOfInterest location;
    
    public SaveReminderViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application app, @org.jetbrains.annotations.NotNull()
    com.udacity.project4.locationreminders.data.ReminderDataSource dataSource) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.Application getApp() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.udacity.project4.locationreminders.data.ReminderDataSource getDataSource() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getReminderTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getReminderDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getReminderSelectedLocationStr() {
        return null;
    }
    
    public final void setReminderSelectedLocationStr(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Double> getLatitude() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Double> getLongitude() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.google.android.gms.maps.model.PointOfInterest> getSelectedPOI() {
        return null;
    }
    
    public final void setSelectedPOI(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<com.google.android.gms.maps.model.PointOfInterest> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.gms.maps.model.PointOfInterest getLocation() {
        return null;
    }
    
    public final void setLocation(@org.jetbrains.annotations.Nullable()
    com.google.android.gms.maps.model.PointOfInterest p0) {
    }
    
    /**
     * Clear the live data objects to start fresh next time the view model gets called
     */
    public final void onClear() {
    }
    
    public final void validateAndSaveReminder(@org.jetbrains.annotations.NotNull()
    com.udacity.project4.locationreminders.reminderslist.ReminderDataItem reminderData) {
    }
    
    public final void saveLocation(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.PointOfInterest location) {
    }
    
    /**
     * Save the reminder to the data source
     */
    public final void saveReminder(@org.jetbrains.annotations.NotNull()
    com.udacity.project4.locationreminders.reminderslist.ReminderDataItem reminderData) {
    }
    
    /**
     * Validate the entered data and show error to the user if there's any invalid data
     */
    public final boolean validateEnteredData(@org.jetbrains.annotations.NotNull()
    com.udacity.project4.locationreminders.reminderslist.ReminderDataItem reminderData) {
        return false;
    }
}