package com.example.shoppingcartapplication_roomdbversion.Objects;

public class CartItemObject {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private final int identity;
    private final int image;
    private final String name;
    private final int price;
    private final int count;

    //---------------------------------------------------------------------------------------//
    //Constructors
    public CartItemObject(int identity, int image, String name, int price, int count) {
        this.identity = identity;
        this.image = image;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    //---------------------------------------------------------------------------------------//
    //Getters and Setters

    public int getIdentity() {
        return identity;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    //---------------------------------------------------------------------------------------//
}
