package com.example.week3.respository

import com.example.week3.model.ProductModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import okhttp3.internal.cache.DiskLruCache.Snapshot

class ProductRepositoryImp:ProductRepository {
    var database:FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref :DatabaseReference =database.reference.child("products")
    override fun addProduct(productModel: ProductModel, callback: (Boolean, String) -> Unit) {
        var id = ref.push().key.toString()
        productModel.productID = id
        ref.child(id).setValue(productModel).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"Product Added successfully")
            }
            else{
                callback(false,it.exception?.message.toString())
            }
        }

    }

    override fun deleteProduct(productID: String, callback: (Boolean, String) -> Unit) {
        ref.child(productID).removeValue().addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"Product Added successfully")
            }
            else{
                callback(false,it.exception?.message.toString())
            }
        }     }

    override fun updateProduct(
        productID: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit){
        ref.child(productID).updateChildren(data).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"Product Added successfully")
            }
            else{
                callback(false,it.exception?.message.toString())
            }
        }
    }

    override fun getProductByID(
        productID: String,
        callback: (ProductModel?, Boolean, String) -> Unit
    ) {


        ref.child(productID).addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var model = snapshot.getValue((ProductModel::class.java))
                        callback(model,true,"Product Fetched")
                    }
                    }

                override fun onCancelled(error: DatabaseError) {
                    callback(null,false,error.message)
                }
            }
                )

    }

    override fun getAllProducts(callback: (List<ProductModel>, Boolean, String) -> Unit) {
        ref.addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var products  = mutableListOf<ProductModel>()
                        for(eachData in snapshot.children){
                            var model = eachData.getValue(ProductModel::class.java)
                            if(model !=null){
                                products.add(model)
                            }
                        }
                        callback(products,true,"Product Fetched")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList(),false,error.message)
                }
            }
        )    }

}