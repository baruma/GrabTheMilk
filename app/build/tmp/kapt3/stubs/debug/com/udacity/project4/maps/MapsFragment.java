package com.udacity.project4.maps;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u00a8\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\n\u0018\u0000 N2\u00020\u00012\u00020\u0002:\u0001NB\u0005\u00a2\u0006\u0002\u0010\u0003J\u0012\u0010(\u001a\u00020)2\b\b\u0002\u0010*\u001a\u00020\u001aH\u0002J\b\u0010+\u001a\u00020\u001aH\u0003J\b\u0010,\u001a\u00020)H\u0002J\b\u0010-\u001a\u00020)H\u0003J\u0018\u0010.\u001a\u00020)2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202H\u0016J&\u00103\u001a\u0004\u0018\u0001042\u0006\u00101\u001a\u0002052\b\u00106\u001a\u0004\u0018\u0001072\b\u00108\u001a\u0004\u0018\u000109H\u0016J\u0010\u0010:\u001a\u00020\u001a2\u0006\u0010;\u001a\u00020<H\u0016J\u0010\u0010=\u001a\u00020)2\u0006\u0010>\u001a\u00020 H\u0016J+\u0010?\u001a\u00020)2\u0006\u0010@\u001a\u00020A2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00050C2\u0006\u0010D\u001a\u00020EH\u0016\u00a2\u0006\u0002\u0010FJ\u0010\u0010G\u001a\u00020)2\u0006\u0010H\u001a\u000209H\u0016J\u001a\u0010I\u001a\u00020)2\u0006\u0010J\u001a\u0002042\b\u00108\u001a\u0004\u0018\u000109H\u0016J\b\u0010K\u001a\u00020)H\u0003J\b\u0010L\u001a\u00020)H\u0002J\u0010\u0010M\u001a\u00020)2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u0018\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004\u00a2\u0006\u0004\n\u0002\b\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\"\u001a\u00020#8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b&\u0010\'\u001a\u0004\b$\u0010%\u00a8\u0006O"}, d2 = {"Lcom/udacity/project4/maps/MapsFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/google/android/gms/maps/GoogleMap$OnPoiClickListener;", "()V", "TAG", "", "kotlin.jvm.PlatformType", "TAG$1", "binding", "Lcom/udacity/project4/databinding/FragmentMapsBinding;", "callback", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "cameraPosition", "Lcom/google/android/gms/maps/model/CameraPosition;", "defaultLocation", "Lcom/google/android/gms/maps/model/LatLng;", "fusedLocationProviderClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "gmapKey", "getGmapKey", "()Ljava/lang/String;", "setGmapKey", "(Ljava/lang/String;)V", "lastKnownLocation", "Landroid/location/Location;", "locationPermissionGranted", "", "map", "Lcom/google/android/gms/maps/GoogleMap;", "placesClient", "Lcom/google/android/libraries/places/api/net/PlacesClient;", "pointOfInterest", "Lcom/google/android/gms/maps/model/PointOfInterest;", "runningQOrLater", "saveReminderViewModel", "Lcom/udacity/project4/locationreminders/savereminder/SaveReminderViewModel;", "getSaveReminderViewModel", "()Lcom/udacity/project4/locationreminders/savereminder/SaveReminderViewModel;", "saveReminderViewModel$delegate", "Lkotlin/Lazy;", "checkDeviceLocationSettingsAndStartGeofence", "", "resolve", "foregroundAndBackgroundLocationPermissionApproved", "getLocationPermission", "getUserLocation", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/View;", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onPoiClick", "poi", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onSaveInstanceState", "outState", "onViewCreated", "view", "requestForegroundAndBackgroundLocationPermissions", "saveLocation", "setMapStyle", "Companion", "app_debug"})
public final class MapsFragment extends androidx.fragment.app.Fragment implements com.google.android.gms.maps.GoogleMap.OnPoiClickListener {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy saveReminderViewModel$delegate = null;
    private final java.lang.String TAG$1 = null;
    private com.udacity.project4.databinding.FragmentMapsBinding binding;
    private final boolean runningQOrLater = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String gmapKey = "AIzaSyCfQgd752YNbd72dOovld2mYIlqima3id4";
    private com.google.android.gms.maps.GoogleMap map;
    private com.google.android.gms.maps.model.CameraPosition cameraPosition;
    private com.google.android.libraries.places.api.net.PlacesClient placesClient;
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationProviderClient;
    private com.google.android.gms.maps.model.LatLng defaultLocation;
    private boolean locationPermissionGranted = false;
    private android.location.Location lastKnownLocation;
    private com.google.android.gms.maps.model.PointOfInterest pointOfInterest;
    private final com.google.android.gms.maps.OnMapReadyCallback callback = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.udacity.project4.maps.MapsFragment.Companion Companion = null;
    private static final java.lang.String TAG = null;
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final java.lang.String KEY_CAMERA_POSITION = "camera_position";
    private static final java.lang.String KEY_LOCATION = "location";
    private static final int M_MAX_ENTRIES = 5;
    private java.util.HashMap _$_findViewCache;
    
    public MapsFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel getSaveReminderViewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGmapKey() {
        return null;
    }
    
    public final void setGmapKey(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @java.lang.Override()
    public void onPoiClick(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.PointOfInterest poi) {
    }
    
    @java.lang.Override()
    public void onCreateOptionsMenu(@org.jetbrains.annotations.NotNull()
    android.view.Menu menu, @org.jetbrains.annotations.NotNull()
    android.view.MenuInflater inflater) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onSaveInstanceState(@org.jetbrains.annotations.NotNull()
    android.os.Bundle outState) {
    }
    
    private final void saveLocation() {
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
    
    private final void setMapStyle(com.google.android.gms.maps.GoogleMap map) {
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final void getUserLocation() {
    }
    
    @android.annotation.TargetApi(value = 29)
    private final boolean foregroundAndBackgroundLocationPermissionApproved() {
        return false;
    }
    
    @android.annotation.TargetApi(value = 29)
    private final void requestForegroundAndBackgroundLocationPermissions() {
    }
    
    private final void checkDeviceLocationSettingsAndStartGeofence(boolean resolve) {
    }
    
    private final void getLocationPermission() {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n \u000b*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/udacity/project4/maps/MapsFragment$Companion;", "", "()V", "DEFAULT_ZOOM", "", "KEY_CAMERA_POSITION", "", "KEY_LOCATION", "M_MAX_ENTRIES", "PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION", "TAG", "kotlin.jvm.PlatformType", "checkDeviceLocationSettingsAndStartGeofence", "", "mapsFragment", "Lcom/udacity/project4/maps/MapsFragment;", "resolve", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        private final void checkDeviceLocationSettingsAndStartGeofence(com.udacity.project4.maps.MapsFragment mapsFragment, boolean resolve) {
        }
    }
}