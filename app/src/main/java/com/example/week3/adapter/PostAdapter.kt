package com.example.week3.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week3.R
import com.example.week3.model.PostModel
import kotlin.math.min

class PostAdapter(private val context: Context, private val posts: MutableList<PostModel>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]

        // Set Profile Image (Using Glide to load image from URL)
        val profileImageUrl = post.profileImage ?: ""
        if (profileImageUrl.isNotEmpty()) {
            Glide.with(context)
                .load(profileImageUrl)
                .circleCrop()
                .into(holder.ivProfileImage)
        } else {
            holder.ivProfileImage.setImageResource(R.drawable.baseline_person_24)
        }

        // Set Username
        holder.tvUsername.text = post.username ?: "Unknown User"

        // Set Post Image (Using Glide to load image from URL)
        val postImageUrl = post.postImage ?: "" // Get the image URL

// Check if the URL uses HTTP and change it to HTTPS
        val securePostImageUrl = if (postImageUrl.startsWith("http://", ignoreCase = true)) {
            postImageUrl.replace("http://", "https://", ignoreCase = true)
        } else {
            postImageUrl
        }

// Load the image with Glide using the updated URL
        if (securePostImageUrl.isNotEmpty()) {
            Glide.with(context)
                .load(securePostImageUrl)
                .into(holder.ivPostImage)
        } else {
            holder.ivPostImage.setImageResource(R.drawable.min)
        }

        // Set Caption
        holder.tvCaption.text = post.caption ?: "No caption provided"

        // Set Timestamp
        holder.tvTimestamp.text = post.timestamp ?: "Unknown time"
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    // Add a new post to the top of the list
    fun addPost(post: PostModel) {
        posts.add(0, post)  // Insert the new post at the top
        notifyItemInserted(0)  // Notify the adapter that a new item is inserted at position 0
    }

    // ViewHolder Class
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProfileImage: ImageView = itemView.findViewById(R.id.ivProfileImage)
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val ivPostImage: ImageView = itemView.findViewById(R.id.ivPostImage)
        val tvCaption: TextView = itemView.findViewById(R.id.tvCaption)
        val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
    }
}
