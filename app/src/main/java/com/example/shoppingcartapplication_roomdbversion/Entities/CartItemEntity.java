package com.example.shoppingcartapplication_roomdbversion.Entities;
//---------------------------------------------------------------------------------------//

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//---------------------------------------------------------------------------------------//
@Entity(foreignKeys = @ForeignKey(entity = ItemEntity.class,
        parentColumns = "identity",
        childColumns = "itemIdentity",
        onDelete = ForeignKey.CASCADE))
public class CartItemEntity {
    //---------------------------------------------------------------------------------------//
    //Attributes
    @PrimaryKey
    private final int itemIdentity;
    private final int count;

    //---------------------------------------------------------------------------------------//
    //Constructors
    public CartItemEntity(int itemIdentity, int count) {
        this.itemIdentity = itemIdentity;
        this.count = count;
    }

    //---------------------------------------------------------------------------------------//
    //Static Getters and Setters
    public static CartItemEntity getDefault(int itemIdentity) {
        return new CartItemEntity(itemIdentity, 1);
    }
    //---------------------------------------------------------------------------------------//
    //Getters and Setter

    public int getItemIdentity() {
        return itemIdentity;
    }

    public int getCount() {
        return count;
    }

    //---------------------------------------------------------------------------------------//
}