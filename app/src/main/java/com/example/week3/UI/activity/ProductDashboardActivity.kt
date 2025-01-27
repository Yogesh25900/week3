package com.example.week3.UI.activity

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week3.R
import com.example.week3.adapter.ProductAdapter
import com.example.week3.databinding.ActivityProductDashboardBinding
import com.example.week3.respository.ProductRepositoryImp
import com.example.week3.viewmodel.ProductViewModel
import java.util.ArrayList

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
        val repo = ProductRepositoryImp()
        productViewModel =ProductViewModel(repo)

        productAdapter = ProductAdapter(this@ProductDashboardActivity, ArrayList())

        productViewModel.getAllProducts()

        productViewModel.AllProducts.observe(this@ProductDashboardActivity){
            it?.let {
                productAdapter.updateData(it)
            }
        }

        productViewModel.loading.observe(this){
            loading->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }
        binding.productlist.adapter = productAdapter
        binding.productlist.layoutManager= (LinearLayoutManager(this))
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



        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, // dragDirs
            ItemTouchHelper.LEFT // swipeDirs (swiping left)
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var productid = productAdapter.getProductbyid(viewHolder.adapterPosition)
                productViewModel.deleteProduct(productid){
                    success,message ->
                    if(success){
                        Toast.makeText(this@ProductDashboardActivity,message,Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@ProductDashboardActivity,message,Toast.LENGTH_SHORT).show()

                    }
                }
            }

        }).attachToRecyclerView(binding.productlist)
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
