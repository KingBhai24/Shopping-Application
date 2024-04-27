package com.example.shoppingcartapplication_roomdbversion.Entities;
//---------------------------------------------------------------------------------------//

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//---------------------------------------------------------------------------------------//
@Entity
public class ItemEntity implements Parcelable {
    //---------------------------------------------------------------------------------------//
    //Attributes
    @PrimaryKey(autoGenerate = true)
    private int identity;
    private final String name;
    private final int price;
    private final String description;
    private final int image;
    //---------------------------------------------------------------------------------------//
    //Constructors

    public ItemEntity(int identity, String name, int price, String description, int image) {
        this.identity = identity;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    @Ignore
    public ItemEntity(String name, int price, String description, int image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }
    //---------------------------------------------------------------------------------------//
    //Getters and Setters

    public int getIdentity() {
        return identity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }
    
    //---------------------------------------------------------------------------------------//
    //Parcelable
    protected ItemEntity(Parcel in) {
        identity = in.readInt();
        name = in.readString();
        price = in.readInt();
        description = in.readString();
        image = in.readInt();
    }

    public static final Creator<ItemEntity> CREATOR = new Creator<ItemEntity>() {
        @Override
        public ItemEntity createFromParcel(Parcel in) {
            return new ItemEntity(in);
        }

        @Override
        public ItemEntity[] newArray(int size) {
            return new ItemEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(identity);
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeString(description);
        dest.writeInt(image);
    }
    //---------------------------------------------------------------------------------------//
}
