package com.example.week3.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week3.model.CartModel
import com.example.week3.repository.CartRepository
import com.example.week3.respository.CartRepositoryImp
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val cartRepository: CartRepository = CartRepositoryImp()
    val cartItems = MutableLiveData<List<CartModel>>()
    val cartStatus = MutableLiveData<String>()

    // Add a product to the cart
    fun addToCart(cartModel: CartModel) {
        viewModelScope.launch {
            cartRepository.addToCart(cartModel) { success, message ->
                cartStatus.value = message
            }
        }
    }

    // Remove a product from the cart by productId
    fun removeFromCartById(productId: String) {
        viewModelScope.launch {
            cartRepository.removeFromCartById(productId) { success, message ->
                cartStatus.value = message
            }
        }
    }

    // Fetch all cart items
    fun getAllCart() {
        viewModelScope.launch {
            cartRepository.getAllCart { cartList, success, message ->
                if (success) {
                    cartItems.value = cartList
                } else {
                    cartStatus.value = message
                }
            }
        }
    }

    // Update cart data
    fun updateCart(cartId: String, data: MutableMap<String, Any>) {
        viewModelScope.launch {
            cartRepository.updateCart(cartId, data) { success, message ->
                cartStatus.value = message
            }
        }
    }

    // Clear a cart by cartId
    fun clearCart(cartId: String) {
        viewModelScope.launch {
            cartRepository.clearCart(cartId) { success, message ->
                cartStatus.value = message
            }
        }
    }
}
