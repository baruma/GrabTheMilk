package com.udacity.project4.locationreminders;

import java.lang.System;

/**
 * Activity that displays the reminder details after the user clicks on the notification
 */
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0014"}, d2 = {"Lcom/udacity/project4/locationreminders/ReminderDescriptionActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "_viewModel", "Lcom/udacity/project4/ReminderDescriptionViewModel;", "get_viewModel", "()Lcom/udacity/project4/ReminderDescriptionViewModel;", "_viewModel$delegate", "Lkotlin/Lazy;", "binding", "Lcom/udacity/project4/databinding/ActivityReminderDescriptionBinding;", "reminder", "Lcom/udacity/project4/locationreminders/data/dto/ReminderDTO;", "getReminder", "()Lcom/udacity/project4/locationreminders/data/dto/ReminderDTO;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "app_debug"})
public final class ReminderDescriptionActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy _viewModel$delegate = null;
    private com.udacity.project4.databinding.ActivityReminderDescriptionBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final com.udacity.project4.locationreminders.data.dto.ReminderDTO reminder = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.udacity.project4.locationreminders.ReminderDescriptionActivity.Companion Companion = null;
    private static final java.lang.String EXTRA_ReminderDataItem = "EXTRA_ReminderDataItem";
    private java.util.HashMap _$_findViewCache;
    
    public ReminderDescriptionActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.udacity.project4.ReminderDescriptionViewModel get_viewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.udacity.project4.locationreminders.data.dto.ReminderDTO getReminder() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/udacity/project4/locationreminders/ReminderDescriptionActivity$Companion;", "", "()V", "EXTRA_ReminderDataItem", "", "newIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "reminderDataItem", "Lcom/udacity/project4/locationreminders/reminderslist/ReminderDataItem;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Intent newIntent(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        com.udacity.project4.locationreminders.reminderslist.ReminderDataItem reminderDataItem) {
            return null;
        }
    }
}