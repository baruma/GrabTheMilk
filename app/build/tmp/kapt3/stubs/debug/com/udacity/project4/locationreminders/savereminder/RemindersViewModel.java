package com.udacity.project4.locationreminders.savereminder;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0018B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b8F\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2 = {"Lcom/udacity/project4/locationreminders/savereminder/RemindersViewModel;", "Landroidx/lifecycle/ViewModel;", "app", "Landroid/app/Application;", "dataSource", "Lcom/udacity/project4/locationreminders/data/ReminderDataSource;", "(Landroid/app/Application;Lcom/udacity/project4/locationreminders/data/ReminderDataSource;)V", "_geofenceRequest", "Landroidx/lifecycle/MutableLiveData;", "Lcom/google/android/gms/location/GeofencingRequest;", "geofenceRequest", "Landroidx/lifecycle/LiveData;", "getGeofenceRequest", "()Landroidx/lifecycle/LiveData;", "createGeofenceRequestForExistingPOI", "", "fetchCoordinatesFromDB", "", "Lcom/udacity/project4/locationreminders/savereminder/RemindersViewModel$SimplePOI;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGeofencingRequest", "latitude", "", "longitude", "SimplePOI", "app_debug"})
public final class RemindersViewModel extends androidx.lifecycle.ViewModel {
    private final com.udacity.project4.locationreminders.data.ReminderDataSource dataSource = null;
    private final androidx.lifecycle.MutableLiveData<com.google.android.gms.location.GeofencingRequest> _geofenceRequest = null;
    
    public RemindersViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application app, @org.jetbrains.annotations.NotNull()
    com.udacity.project4.locationreminders.data.ReminderDataSource dataSource) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.google.android.gms.location.GeofencingRequest> getGeofenceRequest() {
        return null;
    }
    
    private final java.lang.Object fetchCoordinatesFromDB(kotlin.coroutines.Continuation<? super java.util.List<com.udacity.project4.locationreminders.savereminder.RemindersViewModel.SimplePOI>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.android.gms.location.GeofencingRequest getGeofencingRequest(double latitude, double longitude) {
        return null;
    }
    
    public final void createGeofenceRequestForExistingPOI() {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/udacity/project4/locationreminders/savereminder/RemindersViewModel$SimplePOI;", "", "name", "", "latitude", "", "longitude", "(Ljava/lang/String;DD)V", "getLatitude", "()D", "getLongitude", "getName", "()Ljava/lang/String;", "app_debug"})
    public static final class SimplePOI {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String name = null;
        private final double latitude = 0.0;
        private final double longitude = 0.0;
        
        public SimplePOI(@org.jetbrains.annotations.NotNull()
        java.lang.String name, double latitude, double longitude) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getName() {
            return null;
        }
        
        public final double getLatitude() {
            return 0.0;
        }
        
        public final double getLongitude() {
            return 0.0;
        }
    }
}