package com.example.week3.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.week3.model.ProductModel
import com.example.week3.respository.ProductRepository

class ProductViewModel (val repo :ProductRepository) {
    fun addProduct(productModel: ProductModel, callback: (Boolean, String) -> Unit) {
        repo.addProduct(productModel, callback)
    }

    fun deleteProduct(productID: String, callback: (Boolean, String) -> Unit) {
        repo.deleteProduct(productID, callback)

    }

    fun updateProduct(
        productID: String, data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        repo.updateProduct(productID, data, callback)
    }


    var _products = MutableLiveData<ProductModel>()
    var products = MutableLiveData<ProductModel>()
        get() = _products

    var _getAllProducts = MutableLiveData<List<ProductModel>>()
    var getAllProduct = MutableLiveData<List<ProductModel>>()
        get() = _getAllProducts


    var _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
        get() = _loading


    fun getProductByID(productID: String) {
        repo.getProductByID(productID) { products, success, message ->
            if (success) {
                _products.value = products
            }
        }

    }

    fun getAllProducts(){

    _loading.value = true
        repo.getAllProducts() { products, success, message ->
            if (success) {
                // Update LiveData with the fetched products
                _getAllProducts.value = products
                _loading.value = false


            }

        }
    }
}