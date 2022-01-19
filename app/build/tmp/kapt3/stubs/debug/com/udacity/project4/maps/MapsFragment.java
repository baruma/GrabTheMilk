package com.udacity.project4.maps;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u00a0\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\b\u0018\u0000 C2\u00020\u00012\u00020\u0002:\u0001CB\u0005\u00a2\u0006\u0002\u0010\u0003J\u0012\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010 \u001a\u00020\u0017H\u0002J\b\u0010!\u001a\u00020\u0017H\u0003J\b\u0010\"\u001a\u00020\u001fH\u0002J\b\u0010#\u001a\u00020\u001fH\u0003J\u0018\u0010$\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020(H\u0016J&\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010\'\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-2\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J\u0010\u00100\u001a\u00020\u00172\u0006\u00101\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020\u001f2\u0006\u00104\u001a\u000205H\u0016J+\u00106\u001a\u00020\u001f2\u0006\u00107\u001a\u0002082\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u000f0:2\u0006\u0010;\u001a\u00020<H\u0016\u00a2\u0006\u0002\u0010=J\u0010\u0010>\u001a\u00020\u001f2\u0006\u0010?\u001a\u00020/H\u0016J\u001a\u0010@\u001a\u00020\u001f2\u0006\u0010A\u001a\u00020*2\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J\b\u0010B\u001a\u00020\u001fH\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006D"}, d2 = {"Lcom/udacity/project4/maps/MapsFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/google/android/gms/maps/GoogleMap$OnPoiClickListener;", "()V", "binding", "Lcom/udacity/project4/databinding/FragmentMapsBinding;", "callback", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "cameraPosition", "Lcom/google/android/gms/maps/model/CameraPosition;", "defaultLocation", "Lcom/google/android/gms/maps/model/LatLng;", "fusedLocationProviderClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "gmapKey", "", "getGmapKey", "()Ljava/lang/String;", "setGmapKey", "(Ljava/lang/String;)V", "lastKnownLocation", "Landroid/location/Location;", "locationPermissionGranted", "", "map", "Lcom/google/android/gms/maps/GoogleMap;", "placesClient", "Lcom/google/android/libraries/places/api/net/PlacesClient;", "pointOfInterest", "runningQOrLater", "checkDeviceLocationSettingsAndStartGeofence", "", "resolve", "foregroundAndBackgroundLocationPermissionApproved", "getLocationPermission", "getUserLocation", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/View;", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onPoiClick", "poi", "Lcom/google/android/gms/maps/model/PointOfInterest;", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onSaveInstanceState", "outState", "onViewCreated", "view", "requestForegroundAndBackgroundLocationPermissions", "Companion", "app_debug"})
public final class MapsFragment extends androidx.fragment.app.Fragment implements com.google.android.gms.maps.GoogleMap.OnPoiClickListener {
    private com.udacity.project4.databinding.FragmentMapsBinding binding;
    private final boolean runningQOrLater = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String gmapKey = "AIzaSyCfQgd752YNbd72dOovld2mYIlqima3id4";
    private com.google.android.gms.maps.GoogleMap map;
    private com.google.android.gms.maps.model.CameraPosition cameraPosition;
    private com.google.android.libraries.places.api.net.PlacesClient placesClient;
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationProviderClient;
    private final com.google.android.gms.maps.model.LatLng defaultLocation = null;
    private boolean locationPermissionGranted = false;
    private android.location.Location lastKnownLocation;
    private com.google.android.gms.maps.model.LatLng pointOfInterest;
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