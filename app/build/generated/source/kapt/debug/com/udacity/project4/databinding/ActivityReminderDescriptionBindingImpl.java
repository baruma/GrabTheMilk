package com.udacity.project4.databinding;
import com.udacity.project4.R;
import com.udacity.project4.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityReminderDescriptionBindingImpl extends ActivityReminderDescriptionBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener descriptionLabelandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.reminder.getValue().description
            //         is viewModel.reminder.getValue().setDescription((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(descriptionLabel);
            // localize variables for thread safety
            // viewModel.reminder.getValue().description
            java.lang.String viewModelReminderDescription = null;
            // viewModel.reminder != null
            boolean viewModelReminderJavaLangObjectNull = false;
            // viewModel.reminder
            androidx.lifecycle.LiveData<com.udacity.project4.locationreminders.data.dto.ReminderDTO> viewModelReminder = null;
            // viewModel
            com.udacity.project4.locationreminders.savereminder.RemindersViewModel viewModel = mViewModel;
            // viewModel.reminder.getValue()
            com.udacity.project4.locationreminders.data.dto.ReminderDTO viewModelReminderGetValue = null;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.reminder.getValue() != null
            boolean viewModelReminderGetValueJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelReminder = viewModel.getReminder();

                viewModelReminderJavaLangObjectNull = (viewModelReminder) != (null);
                if (viewModelReminderJavaLangObjectNull) {


                    viewModelReminderGetValue = viewModelReminder.getValue();

                    viewModelReminderGetValueJavaLangObjectNull = (viewModelReminderGetValue) != (null);
                    if (viewModelReminderGetValueJavaLangObjectNull) {




                        viewModelReminderGetValue.setDescription(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener locationLabelandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.reminder.getValue().location
            //         is viewModel.reminder.getValue().setLocation((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(locationLabel);
            // localize variables for thread safety
            // viewModel.reminder.getValue().location
            java.lang.String viewModelReminderLocation = null;
            // viewModel.reminder != null
            boolean viewModelReminderJavaLangObjectNull = false;
            // viewModel.reminder
            androidx.lifecycle.LiveData<com.udacity.project4.locationreminders.data.dto.ReminderDTO> viewModelReminder = null;
            // viewModel
            com.udacity.project4.locationreminders.savereminder.RemindersViewModel viewModel = mViewModel;
            // viewModel.reminder.getValue()
            com.udacity.project4.locationreminders.data.dto.ReminderDTO viewModelReminderGetValue = null;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.reminder.getValue() != null
            boolean viewModelReminderGetValueJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelReminder = viewModel.getReminder();

                viewModelReminderJavaLangObjectNull = (viewModelReminder) != (null);
                if (viewModelReminderJavaLangObjectNull) {


                    viewModelReminderGetValue = viewModelReminder.getValue();

                    viewModelReminderGetValueJavaLangObjectNull = (viewModelReminderGetValue) != (null);
                    if (viewModelReminderGetValueJavaLangObjectNull) {




                        viewModelReminderGetValue.setLocation(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener textLabelandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.reminder.getValue().title
            //         is viewModel.reminder.getValue().setTitle((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(textLabel);
            // localize variables for thread safety
            // viewModel.reminder != null
            boolean viewModelReminderJavaLangObjectNull = false;
            // viewModel.reminder.getValue().title
            java.lang.String viewModelReminderTitle = null;
            // viewModel.reminder
            androidx.lifecycle.LiveData<com.udacity.project4.locationreminders.data.dto.ReminderDTO> viewModelReminder = null;
            // viewModel
            com.udacity.project4.locationreminders.savereminder.RemindersViewModel viewModel = mViewModel;
            // viewModel.reminder.getValue()
            com.udacity.project4.locationreminders.data.dto.ReminderDTO viewModelReminderGetValue = null;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.reminder.getValue() != null
            boolean viewModelReminderGetValueJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelReminder = viewModel.getReminder();

                viewModelReminderJavaLangObjectNull = (viewModelReminder) != (null);
                if (viewModelReminderJavaLangObjectNull) {


                    viewModelReminderGetValue = viewModelReminder.getValue();

                    viewModelReminderGetValueJavaLangObjectNull = (viewModelReminderGetValue) != (null);
                    if (viewModelReminderGetValueJavaLangObjectNull) {




                        viewModelReminderGetValue.setTitle(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };

    public ActivityReminderDescriptionBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private ActivityReminderDescriptionBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            );
        this.descriptionLabel.setTag(null);
        this.locationLabel.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.textLabel.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.viewModel == variableId) {
            setViewModel((com.udacity.project4.locationreminders.savereminder.RemindersViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.udacity.project4.locationreminders.savereminder.RemindersViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelReminder((androidx.lifecycle.LiveData<com.udacity.project4.locationreminders.data.dto.ReminderDTO>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelReminder(androidx.lifecycle.LiveData<com.udacity.project4.locationreminders.data.dto.ReminderDTO> ViewModelReminder, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String viewModelReminderDescription = null;
        com.udacity.project4.locationreminders.data.dto.ReminderDTO viewModelReminderGetValue = null;
        java.lang.String viewModelReminderLocation = null;
        java.lang.String viewModelReminderTitle = null;
        androidx.lifecycle.LiveData<com.udacity.project4.locationreminders.data.dto.ReminderDTO> viewModelReminder = null;
        com.udacity.project4.locationreminders.savereminder.RemindersViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x7L) != 0) {



                if (viewModel != null) {
                    // read viewModel.reminder
                    viewModelReminder = viewModel.getReminder();
                }
                updateLiveDataRegistration(0, viewModelReminder);


                if (viewModelReminder != null) {
                    // read viewModel.reminder.getValue()
                    viewModelReminderGetValue = viewModelReminder.getValue();
                }


                if (viewModelReminderGetValue != null) {
                    // read viewModel.reminder.getValue().description
                    viewModelReminderDescription = viewModelReminderGetValue.getDescription();
                    // read viewModel.reminder.getValue().location
                    viewModelReminderLocation = viewModelReminderGetValue.getLocation();
                    // read viewModel.reminder.getValue().title
                    viewModelReminderTitle = viewModelReminderGetValue.getTitle();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.descriptionLabel, viewModelReminderDescription);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.locationLabel, viewModelReminderLocation);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.textLabel, viewModelReminderTitle);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.descriptionLabel, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, descriptionLabelandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.locationLabel, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, locationLabelandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.textLabel, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, textLabelandroidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.reminder
        flag 1 (0x2L): viewModel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}