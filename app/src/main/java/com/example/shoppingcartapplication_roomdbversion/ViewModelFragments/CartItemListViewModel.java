package com.example.shoppingcartapplication_roomdbversion.ViewModelFragments;
//---------------------------------------------------------------------------------------//

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shoppingcartapplication_roomdbversion.Entities.CartItemEntity;
import com.example.shoppingcartapplication_roomdbversion.Entities.ItemEntity;
import com.example.shoppingcartapplication_roomdbversion.Helpers.Repository;
import com.example.shoppingcartapplication_roomdbversion.Objects.CartItemObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//---------------------------------------------------------------------------------------//
public class CartItemListViewModel extends AndroidViewModel {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private static final String LOG_TAG = "CartItemListViewModel";
    private final Repository repository;
    LiveData<List<CartItemEntity>> cartItemEntities;

    //---------------------------------------------------------------------------------------//
    //Constructors
    public CartItemListViewModel(@NonNull Application application) {
        super(application);
        Log.i(LOG_TAG, "CartItemListViewModel(@NonNull Application application)");
        repository = new Repository(application);
        cartItemEntities = repository.getCartItemEntities();
    }

    //---------------------------------------------------------------------------------------//

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(LOG_TAG, "onCleared()");
    }

    //---------------------------------------------------------------------------------------//
    //Getters and Setters
    public LiveData<List<CartItemEntity>> getCartItemEntities() {
        return cartItemEntities;
    }

    public List<CartItemObject> getCartItemObjects(List<CartItemEntity> cartItemEntities) {
        List<CartItemObject> temp = new ArrayList<>();
        for (CartItemEntity n : cartItemEntities) {
            ItemEntity x = repository.getItemEntity(n.getItemIdentity());
            temp.add(new CartItemObject(x.getIdentity(), x.getImage(), x.getName(), x.getPrice(), n.getCount()));
        }
        return temp;
    }

    //---------------------------------------------------------------------------------------//
    //CRUD Functions
    public void countIncreased(int itemIdentity) {
        int n = repository.getCartItemEntityCount(itemIdentity) + 1;
        repository.updateCartItemEntity(new CartItemEntity(itemIdentity, n));
    }

    public void countDecreased(int itemIdentity) {
        int n = repository.getCartItemEntityCount(itemIdentity) - 1;
        if (n == 0)
            delete(itemIdentity);
        else
            repository.updateCartItemEntity(new CartItemEntity(itemIdentity, n));
    }

    public void delete(int itemIdentity) {
        repository.deleteCartItemEntity(itemIdentity);
    }

    public void deleteSelectedCartItems(HashSet<Integer> selectedItems) {
        for (int k : selectedItems) {
            delete(k);
        }
    }
    //---------------------------------------------------------------------------------------//
}