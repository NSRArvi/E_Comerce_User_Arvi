package com.example.e_comerce_user.callbacks;


import com.example.e_comerce_user.models.CartModel;

public interface AddRemoveCartItemListener {
    void onCartItemAdd(CartModel cartModel, int position);
    void onCartItemRemove(String productId, int position);
}
