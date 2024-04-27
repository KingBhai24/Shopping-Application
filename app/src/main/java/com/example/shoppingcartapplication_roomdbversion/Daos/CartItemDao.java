package com.example.shoppingcartapplication_roomdbversion.Daos;
//---------------------------------------------------------------------------------------//

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoppingcartapplication_roomdbversion.Entities.CartItemEntity;

import java.util.List;

//---------------------------------------------------------------------------------------//
@Dao
public interface CartItemDao {
    //---------------------------------------------------------------------------------------//
    //CRUD Operations
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CartItemEntity cartItemEntity);

    @Update
    void update(CartItemEntity cartItemEntity);

    @Query("update CartItemEntity set count = :count where itemIdentity = :itemIdentity")
    void update(int itemIdentity, int count);

    @Query("delete from CartItemEntity where itemIdentity = :itemIdentity")
    void delete(int itemIdentity);

    @Query("select * from CartItemEntity")
    LiveData<List<CartItemEntity>> getCartItems();

    @Query("select count from CartItemEntity where itemIdentity = :itemIdentity")
    int getCount(int itemIdentity);
    //---------------------------------------------------------------------------------------//
}
