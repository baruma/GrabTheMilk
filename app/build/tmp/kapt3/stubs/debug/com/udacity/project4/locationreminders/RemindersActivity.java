package com.udacity.project4.locationreminders;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0013J\u0012\u0010\"\u001a\u00020\u001e2\b\u0010#\u001a\u0004\u0018\u00010$H\u0014J\u0010\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020(H\u0016J-\u0010)\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020+2\u000e\u0010,\u001a\n\u0012\u0006\b\u0001\u0012\u00020.0-2\u0006\u0010/\u001a\u000200H\u0016\u00a2\u0006\u0002\u00101R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\b\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c\u00a8\u00062"}, d2 = {"Lcom/udacity/project4/locationreminders/RemindersActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "_viewModel", "Lcom/udacity/project4/locationreminders/savereminder/RemindersViewModel;", "get_viewModel", "()Lcom/udacity/project4/locationreminders/savereminder/RemindersViewModel;", "_viewModel$delegate", "Lkotlin/Lazy;", "defaultLocation", "Lcom/google/android/gms/maps/model/LatLng;", "geofenceList", "", "Lcom/google/android/gms/location/Geofence;", "getGeofenceList", "()Ljava/util/List;", "setGeofenceList", "(Ljava/util/List;)V", "geofencePendingIntent", "Landroid/app/PendingIntent;", "getGeofencePendingIntent", "()Landroid/app/PendingIntent;", "geofencePendingIntent$delegate", "geofencingClient", "Lcom/google/android/gms/location/GeofencingClient;", "getGeofencingClient", "()Lcom/google/android/gms/location/GeofencingClient;", "setGeofencingClient", "(Lcom/google/android/gms/location/GeofencingClient;)V", "addGeofence", "", "request", "Lcom/google/android/gms/location/GeofencingRequest;", "intent", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "", "item", "Landroid/view/MenuItem;", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "app_debug"})
public final class RemindersActivity extends androidx.appcompat.app.AppCompatActivity {
    public com.google.android.gms.location.GeofencingClient geofencingClient;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy _viewModel$delegate = null;
    private com.google.android.gms.maps.model.LatLng defaultLocation;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.google.android.gms.location.Geofence> geofenceList;
    private final kotlin.Lazy geofencePendingIntent$delegate = null;
    private java.util.HashMap _$_findViewCache;
    
    public RemindersActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.android.gms.location.GeofencingClient getGeofencingClient() {
        return null;
    }
    
    public final void setGeofencingClient(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.location.GeofencingClient p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.udacity.project4.locationreminders.savereminder.RemindersViewModel get_viewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.google.android.gms.location.Geofence> getGeofenceList() {
        return null;
    }
    
    public final void setGeofenceList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.google.android.gms.location.Geofence> p0) {
    }
    
    private final android.app.PendingIntent getGeofencePendingIntent() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void addGeofence(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.location.GeofencingRequest request, @org.jetbrains.annotations.NotNull()
    android.app.PendingIntent intent) {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
}