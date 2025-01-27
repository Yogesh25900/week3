package com.example.week3.repository

import com.example.week3.model.CartModel

interface CartRepository {

    // Adds a product to the cart
    fun addToCart(cartModel: CartModel, callback: (Boolean, String) -> Unit)

    // Removes a product from the cart by its ID
    fun removeFromCartById(productId: String, callback: (Boolean, String) -> Unit)

    // Retrieves all products in the cart
    fun getAllCart(callback: (List<CartModel>, Boolean, String) -> Unit)

    // Retrieves the cart details by cart ID
    fun getCartById(cartId: String, callback: (Boolean, String) -> Unit)

    // Removes all products from the cart
    fun clearCart(cartId: String, callback: (Boolean, String) -> Unit)

    // Updates a cart with new data
    fun updateCart(cartId: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit)
}
