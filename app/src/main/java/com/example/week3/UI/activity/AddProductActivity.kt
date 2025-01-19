package com.example.week3.UI.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.week3.R
import com.example.week3.R.*
import com.example.week3.databinding.ActivityAddProductBinding
import com.example.week3.model.ProductModel
import com.example.week3.respository.ProductRepositoryImp
import com.example.week3.utils.LoadingUtils
import com.example.week3.viewmodel.ProductViewModel

class AddProductActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddProductBinding
lateinit var viewModel: ProductViewModel

lateinit var loadingutils :LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingutils= LoadingUtils(this)
        val repo = ProductRepositoryImp()
        viewModel = ProductViewModel(repo)

        binding.addProductButton.setOnClickListener {

            loadingutils.show()

            val productname = binding.productName.text.toString()
            val productdesc =binding.description.text.toString()
            val price = binding.price.text.toString().toInt()
            val productModel = ProductModel("",productname, productdesc, price)

            viewModel.addProduct(productModel) { success, message ->
                if (success) {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ProductDashboardActivity::class.java))
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
                loadingutils.dismiss()

            }
            startActivity(Intent(this,ProductDashboardActivity::class.java))
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}