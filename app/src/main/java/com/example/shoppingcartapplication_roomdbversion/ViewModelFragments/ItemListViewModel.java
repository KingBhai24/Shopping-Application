package com.example.shoppingcartapplication_roomdbversion.ViewModelFragments;
//---------------------------------------------------------------------------------------//

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingcartapplication_roomdbversion.Entities.CartItemEntity;
import com.example.shoppingcartapplication_roomdbversion.Entities.ItemEntity;
import com.example.shoppingcartapplication_roomdbversion.Enums.ActionMenuModeEnum;
import com.example.shoppingcartapplication_roomdbversion.Enums.RecyclerViewLayoutManagerEnum;
import com.example.shoppingcartapplication_roomdbversion.Helpers.Repository;

import java.util.HashSet;
import java.util.List;

//---------------------------------------------------------------------------------------//
public class ItemListViewModel extends AndroidViewModel {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private static final String LOG_TAG = "ItemListViewModel";
    private final List<ItemEntity> items;
    private RecyclerViewLayoutManagerEnum recyclerViewLayoutManagerEnum;
    private final Repository repository;

    //---------------------------------------------------------------------------------------//
    //Constructors

    public ItemListViewModel(@NonNull Application application) {
        super(application);
        Log.i(LOG_TAG, "ItemListViewModel(@NonNull Application application)");
        repository = new Repository(application);
        recyclerViewLayoutManagerEnum = RecyclerViewLayoutManagerEnum.LINEAR_LAYOUT;
        items = repository.getItemEntities();
    }

    //---------------------------------------------------------------------------------------//
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(LOG_TAG, "onCleared()");
    }

    //---------------------------------------------------------------------------------------//
    //Getters and Setter
    public List<ItemEntity> getItems() {
        return items;
    }

    public RecyclerViewLayoutManagerEnum getRecyclerViewLayoutManagerEnum() {
        return recyclerViewLayoutManagerEnum;
    }

    public void setRecyclerViewLayoutManagerEnum(RecyclerViewLayoutManagerEnum recyclerViewLayoutManagerEnum) {
        this.recyclerViewLayoutManagerEnum = recyclerViewLayoutManagerEnum;
    }
    //---------------------------------------------------------------------------------------//
    public void addSelectedItems(HashSet<Integer> selectedItemEntities) {
        for (int k : selectedItemEntities) {
            repository.insertCartItemEntity(CartItemEntity.getDefault(k));
        }
    }
    //---------------------------------------------------------------------------------------//
}