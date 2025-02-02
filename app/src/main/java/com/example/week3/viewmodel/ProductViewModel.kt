package com.example.week3.viewmodel

import android.content.Context
import android.net.Uri
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


    var _products = MutableLiveData<ProductModel?>()
    var products = MutableLiveData<ProductModel?>()
        get() = _products

    var _AllProducts = MutableLiveData<List<ProductModel>>()
    var AllProducts = MutableLiveData<List<ProductModel>>()
        get() = _AllProducts


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
                _AllProducts.value = products
                _loading.value = false


            }

        }
    }
    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit){
        repo.uploadImage(context, imageUri, callback)
    }
}