package com.example.shoppingcartapplication_roomdbversion.ViewModelActivities;
//---------------------------------------------------------------------------------------//

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingcartapplication_roomdbversion.Entities.ItemEntity;
//---------------------------------------------------------------------------------------//

public class MainActivityViewModel extends AndroidViewModel {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private static final String LOG_TAG = "MainActivityViewModel";
    MutableLiveData<ItemEntity> itemEntityItemDetailFragment;

    //---------------------------------------------------------------------------------------//
    //Constructors
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        Log.i(LOG_TAG, "MainActivityViewModel(@NonNull Application application)");
        itemEntityItemDetailFragment = new MutableLiveData<>();
    }
    //---------------------------------------------------------------------------------------//
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(LOG_TAG, "onCleared()");
    }

    //---------------------------------------------------------------------------------------//
    //Getters and Setters
    public void setItemEntityItemDetailFragment(ItemEntity itemEntity) {
        this.itemEntityItemDetailFragment.setValue(itemEntity);
    }

    public LiveData<ItemEntity> getItemEntityItemDetailFragment() {
        return itemEntityItemDetailFragment;
    }
    //---------------------------------------------------------------------------------------//
}
