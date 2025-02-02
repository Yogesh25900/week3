package com.example.week3.respository

import android.content.Context
import android.net.Uri
import com.example.week3.model.ProductModel

interface ProductRepository {

    //callback
    //success:true :Boolean
    //message :"Product added" :string
    fun addProduct(productModel: ProductModel , callback: (Boolean,String) ->Unit)
     fun deleteProduct(productID: String,callback: (Boolean, String) -> Unit)
     fun updateProduct(productID:String,data:MutableMap<String,Any>,
                       callback: (Boolean, String) -> Unit
     )

     fun getProductByID(productID: String,
                        callback: (ProductModel?,Boolean, String) -> Unit)

     fun getAllProducts(callback: (List<ProductModel>,Boolean, String) -> Unit)

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context, uri: Uri): String?

}