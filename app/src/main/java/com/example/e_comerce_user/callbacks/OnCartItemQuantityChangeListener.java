package com.example.e_comerce_user.callbacks;


import com.example.e_comerce_user.models.CartModel;

import java.util.List;

public interface OnCartItemQuantityChangeListener {
    void onCartItemQuantityChange(List<CartModel> models);
}
