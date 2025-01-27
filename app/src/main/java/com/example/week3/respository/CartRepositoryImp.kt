package com.example.week3.respository

import com.example.week3.model.CartModel
import com.example.week3.repository.CartRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.ref.Reference

class CartRepositoryImp: CartRepository {

    var database :FirebaseDatabase = FirebaseDatabase.getInstance()
    var cartRef :DatabaseReference = database.reference.child("cart")
    override fun addToCart(cartModel: CartModel, callback: (Boolean, String) -> Unit) {
        var id  = cartRef.push().key.toString()
        cartModel.cartid = id
        cartRef.child(id).setValue(cartModel).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"Cart successfully added")
            }else{
                callback(false,"Error adding cart")
            }
        }
    }

    override fun removeFromCartById(productId: String, callback: (Boolean, String) -> Unit) {
        cartRef.child(productId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Cart item removed successfully")
            } else {
                callback(false, "Error removing cart item")
            }
        }
    }

    override fun getAllCart(callback: (List<CartModel>, Boolean, String) -> Unit) {
        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartList = mutableListOf<CartModel>()
                for (cartSnapshot in snapshot.children) {
                    val cartItem = cartSnapshot.getValue(CartModel::class.java)
                    cartItem?.let { cartList.add(it) }
                }
                callback(cartList, true, "Cart items retrieved successfully")
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList(), false, "Error retrieving cart items: ${error.message}")
            }
        })
    }


    override fun getCartById(cartId: String, callback: (Boolean, String) -> Unit) {
        cartRef.child(cartId).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val cartItem = it.result?.getValue(CartModel::class.java)
                if (cartItem != null) {
                    callback(true, "Cart item retrieved successfully")
                } else {
                    callback(false, "Cart item not found")
                }
            } else {
                callback(false, "Error retrieving cart item")
            }
        }
    }


    override fun clearCart(cartId: String, callback: (Boolean, String) -> Unit) {
        cartRef.removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "All cart items cleared")
            } else {
                callback(false, "Error clearing cart")
            }
        }
    }


    override fun updateCart(cartId: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        cartRef.child(cartId).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Cart updated successfully")
            } else {
                callback(false, "Error updating cart")
            }
        }
    }



}