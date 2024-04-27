package com.example.shoppingcartapplication_roomdbversion.Helpers;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.shoppingcartapplication_roomdbversion.Daos.CartItemDao;
import com.example.shoppingcartapplication_roomdbversion.Daos.ItemDao;
import com.example.shoppingcartapplication_roomdbversion.Entities.CartItemEntity;
import com.example.shoppingcartapplication_roomdbversion.Entities.ItemEntity;

import java.util.List;

public class Repository {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private final ItemDao itemDao;
    private final CartItemDao cartItemDao;

    //---------------------------------------------------------------------------------------//
    //Constructors
    public Repository(Application application) {
        DatabaseHelper Dbh = DatabaseHelper.getDB(application);
        itemDao = Dbh.getItemDao();
        cartItemDao = Dbh.getCartItemDao();
    }

    //---------------------------------------------------------------------------------------//
    //CRUD Functions
    //ItemEntity
    public void insertItemEntity(ItemEntity itemEntity) {
        itemDao.insert(itemEntity);
    }

    public List<ItemEntity> getItemEntities() {
        return itemDao.getItems();
    }

    public ItemEntity getItemEntity(int identity) {
        return itemDao.getItem(identity);
    }

    //CartItemEntity
    public void insertCartItemEntity(CartItemEntity cartItemEntity) {
        cartItemDao.insert(cartItemEntity);
    }

    public LiveData<List<CartItemEntity>> getCartItemEntities() {
        return cartItemDao.getCartItems();
    }

    public int getCartItemEntityCount(int itemIdentity) {
        return cartItemDao.getCount(itemIdentity);
    }

    public void updateCartItemEntity(CartItemEntity cartItemEntity) {
        cartItemDao.update(cartItemEntity);
    }

    public void deleteCartItemEntity(int itemIdentity) {
        cartItemDao.delete(itemIdentity);
    }

    //---------------------------------------------------------------------------------------//
    //Async Classes ItemEntity
    private class InsertItemEntity extends AsyncTask<ItemEntity, Void, Void> {

        @Override
        protected Void doInBackground(ItemEntity... itemEntities) {
            itemDao.insert(itemEntities[0]);
            return null;
        }
    }

    private class GetItemEntities extends AsyncTask<Void, Void, List<ItemEntity>> {

        @Override
        protected List<ItemEntity> doInBackground(Void... voids) {
            return itemDao.getItems();
        }

        @Override
        protected void onPostExecute(List<ItemEntity> itemEntities) {
            super.onPostExecute(itemEntities);

        }
    }
    private class GetItemEntity extends AsyncTask<Integer, Void, ItemEntity> {

        @Override
        protected ItemEntity doInBackground(Integer... ints) {
            return itemDao.getItem(ints[0]);
        }
    }
    //---------------------------------------------------------------------------------------//

}
