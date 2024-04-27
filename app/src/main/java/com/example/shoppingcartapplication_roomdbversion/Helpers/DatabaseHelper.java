package com.example.shoppingcartapplication_roomdbversion.Helpers;
//---------------------------------------------------------------------------------------//

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shoppingcartapplication_roomdbversion.Daos.CartItemDao;
import com.example.shoppingcartapplication_roomdbversion.Daos.ItemDao;
import com.example.shoppingcartapplication_roomdbversion.Entities.CartItemEntity;
import com.example.shoppingcartapplication_roomdbversion.Entities.ItemEntity;

//---------------------------------------------------------------------------------------//
@Database(entities = {ItemEntity.class, CartItemEntity.class}, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private static final String DB_NAME = "ShoppingCartApplicationDB";
    private static DatabaseHelper instance;

    //---------------------------------------------------------------------------------------//
    //Abstract required methods for Room
    public abstract ItemDao getItemDao();
    public abstract CartItemDao getCartItemDao();
    //---------------------------------------------------------------------------------------//
    //Getters and Setters
    public static synchronized DatabaseHelper getDB(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabaseHelper.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    //---------------------------------------------------------------------------------------//
}
