package com.example.week3.UI.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week3.R
import com.example.week3.adapter.ProductAdapter
import com.example.week3.databinding.ActivityProductDashboardBinding
import com.example.week3.respository.ProductRepositoryImp
import com.example.week3.viewmodel.ProductViewModel

class ProductDashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDashboardBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
//        val repo = ProductRepositoryImp()
//        productViewModel =ProductViewModel(repo)

        // Set up RecyclerView with ViewBinding
//        binding.productlist.layoutManager = LinearLayoutManager(this)

        // Set up the adapter (Initially empty list)
//        productAdapter = ProductAdapter(emptyList())
//        binding.productlist.adapter = productAdapter

        // Observe the LiveData from ViewModel
//        productViewModel.getAllProduct.observe(this, Observer { products ->
//            // Update RecyclerView with the new list of products
//            productAdapter.(products)
//        })

        // Fetch all products from the ViewModel
//        productViewModel.getAllProducts()

        // Handle click for the floating action button to navigate to AddProductActivity
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Handle system window insets (for edge-to-edge UI)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
