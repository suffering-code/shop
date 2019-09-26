package com.qf.service;

import com.qf.entity.Cart;
import com.qf.entity.User;

import java.util.List;

public interface ICartService extends IBaseService<Cart> {
    void addCart(String cartToken, User user, Cart cart);

    List<Cart> getCartList(String cartToken, User user);

    int findCartCount(String cartToken, User user);

    int mergeCart(String cartToken, User user);

    void deleteCartByUserId(Integer id);
}
