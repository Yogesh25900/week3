package com.example.a35b_crud.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week3.R
import com.example.week3.UI.activity.UpdateProductActivity
import com.example.week3.model.ProductModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.ArrayList

class ProductAdapter(val context: Context,
                     var data : ArrayList<ProductModel>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    class ProductViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){

        val imageView : ImageView = itemView.findViewById(R.id.getImage)
        val loading : ProgressBar = itemView.findViewById(R.id.progressBar2)
        val editButton : Button = itemView.findViewById(R.id.editbtn)
        val pName : TextView = itemView.findViewById(R.id.displayName)
        val pPrice : TextView = itemView.findViewById(R.id.displayPrice)
        val pDesc : TextView = itemView.findViewById(R.id.displayDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView : View = LayoutInflater.from(context).
        inflate(R.layout.item_product,parent,false)

        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.pName.text = data[position].productName
        holder.pPrice.text = data[position].price.toString()
        holder.pDesc.text = data[position].productDesc

        Picasso.get().load(data[position].imageUrl).into(holder.imageView,object:Callback{
            override fun onSuccess() {
                holder.loading.visibility = View.GONE
            }

            override fun onError(e: Exception?) {

            }

        })


        holder.editButton.setOnClickListener {
            val intent = Intent(context, UpdateProductActivity::class.java)
//            if model pass garnu paryo bhane
//            first make model parcelable
//            intent.putExtra("products",data[position])

            intent.putExtra("productId",data[position].productID)

            context.startActivity(intent)
        }
    }

    fun updateData(products: List<ProductModel>){
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()
    }

    fun getProductId(position: Int) : String? {
        return data[position].productID
    }

}