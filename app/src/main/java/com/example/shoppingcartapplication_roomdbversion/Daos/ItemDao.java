package com.example.shoppingcartapplication_roomdbversion.Daos;
//---------------------------------------------------------------------------------------//

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shoppingcartapplication_roomdbversion.Entities.ItemEntity;

import java.util.List;

//---------------------------------------------------------------------------------------//
@Dao
public interface ItemDao {
    //---------------------------------------------------------------------------------------//
    //CRUD Operations
    @Insert
    void insert(ItemEntity item);

    @Query("select * from ItemEntity")
    List<ItemEntity> getItems();

    @Query("select * from ItemEntity where identity = :identity")
    ItemEntity getItem(int identity);
    //---------------------------------------------------------------------------------------//
}
