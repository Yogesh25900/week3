package com.example.week3.UI.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.week3.R
import com.example.week3.model.PostModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddPostActivity : Activity() {

    private lateinit var etCaption: EditText
    private lateinit var ivPostImage: ImageView
    private lateinit var btnUploadPost: Button

    private lateinit var databaseReference: DatabaseReference

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Posts")

        // Initialize Cloudinary
//        MediaManager.init(this)
        initializeCloudinary()
        etCaption = findViewById(R.id.etCaption)
        ivPostImage = findViewById(R.id.ivPostImage)
        btnUploadPost = findViewById(R.id.addpost)

        ivPostImage.setOnClickListener {
            openGallery()
        }

        btnUploadPost.setOnClickListener {
            uploadPostToCloudinary()
        }
    }

    // Open gallery to pick image
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            Log.d("URI" , "imageuri:$imageUri")
            ivPostImage.setImageURI(imageUri)
        }
    }

    // Upload post data to Cloudinary and then save the URL to Firebase
    private fun uploadPostToCloudinary() {
        val caption = etCaption.text.toString()

        if (caption.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please enter a caption and select an image", Toast.LENGTH_SHORT).show()
            return
        }
        val uploadRequest = MediaManager.get().upload(imageUri).callback(object : UploadCallback {
            override fun onStart(requestId: String) {
            }

            override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                // Update progress if needed
            }

            override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                val imageUrl = resultData["url"].toString()
                Log.d("Cloudinary", "Upload successful: ${resultData["url"]}")

                savePostToFirebase(caption, imageUrl)
            }

            override fun onReschedule(requestId: String, error: ErrorInfo) {
            }

            override fun onError(requestId: String, error: ErrorInfo) {
                Toast.makeText(this@AddPostActivity, "Upload failed: ${error.description}", Toast.LENGTH_SHORT).show()
            }
        })

        uploadRequest.dispatch()
    }

    //firebase
    private fun savePostToFirebase(caption: String, imageUrl: String) {
        val postId = databaseReference.push().key ?: return
        val post = PostModel(
            profileImage = "https://www.psychologs.com/wp-content/uploads/2024/01/8-tips-to-be-a-jolly-person.jpg", // Use a default or stored profile image URL
            username = "Yogesh",
            postImage = imageUrl,
            caption = caption,
            timestamp = System.currentTimeMillis().toString()
        )

        // Save post to Firebase Realtime Database
        databaseReference.child(postId).setValue(post)
            .addOnSuccessListener {
                Toast.makeText(this, "Post uploaded successfully!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to upload post: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun initializeCloudinary() {
        // Initialize Cloudinary with your configuration (if needed)
        val config = HashMap<String, String>()
        config["cloud_name"] = "drykew7pu"
        config["api_key"] = "891342176588327"
        config["api_secret"] = "-7N8kuVvR0FNLLPYFModBB_03UM"

        MediaManager.init(this, config)
        Log.d("Cloudinary","Cloudinary Initialization Successfull")
    }
}
