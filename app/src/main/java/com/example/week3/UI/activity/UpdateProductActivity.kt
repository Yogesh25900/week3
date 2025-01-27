package com.example.week3.UI.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.week3.R
import com.example.week3.databinding.ActivityUpdateProductBinding
import com.example.week3.model.ProductModel
import com.example.week3.respository.ProductRepositoryImp
import com.example.week3.viewmodel.ProductViewModel

class UpdateProductActivity : AppCompatActivity() {
    lateinit var  productViewModel: ProductViewModel
    lateinit var  binding :ActivityUpdateProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


//       var products : ProductModel? = intent.getParcelableExtra("products")
        var productid:String = intent.getSerializableExtra("products").toString()
        var repo = ProductRepositoryImp()
        productViewModel = ProductViewModel(repo)
        productViewModel.getProductByID(productid)

            productViewModel.products.observe(this){
                binding.updateproductName.setText(it?.productName.toString())
           binding.updateprice.setText(it?.price.toString())
           binding.updatedescription.setText(it?.productDesc.toString())

            }


        binding.updateproductbutton.setOnClickListener {
            var name = binding.updateproductName.text.toString()
            var price = binding.updateprice.text.toString().toInt()
            var desc = binding.updatedescription.text.toString()
            var updatedata = mutableMapOf<String,Any>()
            updatedata["productName"] =name
            updatedata["price"] =price
            updatedata["productDesc"] =desc
            productViewModel.updateProduct(productid,updatedata){
                success,message ->
                    if(success){
                        Toast.makeText(this,"Sucessfully updated data",Toast.LENGTH_SHORT).show()

                        finish()

                    }else{
                        Toast.makeText(this,"Error updating data",Toast.LENGTH_SHORT).show()
                    }

            }
        }
//        products.let{
//            binding.updateproductName.setText(it?.productName.toString())
//            binding.updateprice.setText(it?.price.toString())
//            binding.updatedescription.setText(it?.productDesc.toString())
//        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}