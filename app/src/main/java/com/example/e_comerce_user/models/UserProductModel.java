package com.example.e_comerce_user.models;

public class UserProductModel extends ProductModel{
    private boolean isInCart;
    private boolean isFavorite;

    public boolean isInCart() {
        return isInCart;
    }

    public void setInCart(boolean inCart) {
        isInCart = inCart;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
