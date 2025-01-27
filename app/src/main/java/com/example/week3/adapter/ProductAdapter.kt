package com.example.week3.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week3.R
import com.example.week3.UI.activity.UpdateProductActivity
import com.example.week3.model.ProductModel

class ProductAdapter(
    var context : Context,
    var data : ArrayList<ProductModel>
):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var productName :TextView= itemView.findViewById(R.id.productName)
        var productDesc :TextView= itemView.findViewById(R.id.productDesc)
        var price :TextView= itemView.findViewById(R.id.productPrice)
        var edittbn : Button = itemView.findViewById(R.id.editbtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return  ProductViewHolder(itemView )
    }

    override fun getItemCount(): Int {
            return  data.size
    }

    fun updateData(Products:List<ProductModel>){
        data.clear()
        data.addAll(Products)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text = data[position].productName
        holder.productDesc.text = data[position].productDesc
        holder.price.text = data[position].price.toString()

        holder.edittbn.setOnClickListener {

          val intent = Intent(context,UpdateProductActivity::class.java)
            intent.putExtra("products",data[position].productID)
            context.startActivity(intent)

        }

    }

    fun getProductbyid(position: Int):String
    {
        return data[position].productID.toString()
    }

}



