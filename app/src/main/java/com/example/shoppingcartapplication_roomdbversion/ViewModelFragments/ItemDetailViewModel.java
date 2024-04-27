package com.example.shoppingcartapplication_roomdbversion.ViewModelFragments;
//---------------------------------------------------------------------------------------//

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.shoppingcartapplication_roomdbversion.Entities.CartItemEntity;
import com.example.shoppingcartapplication_roomdbversion.Helpers.Repository;

//---------------------------------------------------------------------------------------//
public class ItemDetailViewModel extends AndroidViewModel {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private static final String LOG_TAG = "ItemDetailViewModel";
    private final Repository repository;

    //---------------------------------------------------------------------------------------//
    //Constructors
    public ItemDetailViewModel(@NonNull Application application) {
        super(application);
        Log.i(LOG_TAG, "ItemDetailViewModel(@NonNull Application application)");
        repository = new Repository(application);
    }

    //---------------------------------------------------------------------------------------//
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(LOG_TAG, "onCleared()");
    }

    //---------------------------------------------------------------------------------------//
    public void insertToCartItemEntity(CartItemEntity cartItemEntity) {
        repository.insertCartItemEntity(cartItemEntity);
    }
    //---------------------------------------------------------------------------------------//
}