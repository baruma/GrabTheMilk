package com.udacity.project4.authentication;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\tH\u0014J\b\u0010\n\u001a\u00020\tH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/udacity/project4/authentication/FirebaseUserLiveData;", "Landroidx/lifecycle/LiveData;", "Lcom/google/firebase/auth/FirebaseUser;", "()V", "authStateListener", "Lcom/google/firebase/auth/FirebaseAuth$AuthStateListener;", "firebaseAuth", "Lcom/google/firebase/auth/FirebaseAuth;", "onActive", "", "onInactive", "app_debug"})
public final class FirebaseUserLiveData extends androidx.lifecycle.LiveData<com.google.firebase.auth.FirebaseUser> {
    private final com.google.firebase.auth.FirebaseAuth firebaseAuth = null;
    private final com.google.firebase.auth.FirebaseAuth.AuthStateListener authStateListener = null;
    
    public FirebaseUserLiveData() {
        super(null);
    }
    
    @java.lang.Override()
    protected void onActive() {
    }
    
    @java.lang.Override()
    protected void onInactive() {
    }
}