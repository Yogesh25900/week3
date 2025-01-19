
package com.example.week3.UI.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.week3.databinding.ActivityCloudinaryActivtityBinding

class CloudinaryActivtity : AppCompatActivity() {
    private lateinit var binding: ActivityCloudinaryActivtityBinding

    private val PICK_IMAGE = 1
    private val REQUEST_PERMISSION = 100
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCloudinaryActivtityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    initializeCloudinary()
        // Check and request permission if necessary
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_PERMISSION
            )
        }

        // Button to open gallery
        binding.btPickImage.setOnClickListener {
            accessTheGallery()
        }

        // Button to upload image to Cloudinary
        binding.btSave.setOnClickListener {
            uploadToCloudinary(imageUri)
        }
    }

    // Request permission results
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open gallery
                accessTheGallery()
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied, cannot access gallery", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    // Access gallery to pick an image
    private fun accessTheGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant read permission to the URI
        startActivityForResult(intent, PICK_IMAGE)
    }

    // Handle the result after picking an image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data?.data

            if (imageUri != null) {
                // Display image in ImageView
                binding.imageView.setImageURI(imageUri)

                // Upload image to Cloudinary
                uploadToCloudinary(imageUri)
            } else {
                Log.e("CloudinaryActivity", "Image URI is null")
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Upload image to Cloudinary
    private fun uploadToCloudinary(imageUri: Uri?) {
        if (imageUri != null) {
            val uploadRequest =
                MediaManager.get().upload(imageUri).callback(object : UploadCallback {
                    override fun onStart(requestId: String) {
                        binding.tvStatus.text = "Start uploading..."
                    }

                    override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                        binding.tvStatus.text = "Uploading... ${bytes * 100 / totalBytes}%"
                    }

                    override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                        binding.tvStatus.text = "Upload successful! URL: ${resultData["url"]}"
                        Log.d("Cloudinary", "Upload successful: ${resultData["url"]}")
                    }

                    override fun onReschedule(requestId: String, error: ErrorInfo) {
                        Log.e("Cloudinary", "Upload rescheduled: ${error.description}")
                        binding.tvStatus.text = "Upload rescheduled: ${error.description}"
                    }

                    override fun onError(requestId: String, error: ErrorInfo) {
                        Log.e("Cloudinary", "Upload error: ${error.description}")
                        binding.tvStatus.text = "Upload failed: ${error.description}"
                    }
                })

            // Dispatch the upload request
            uploadRequest.dispatch()
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initializeCloudinary() {
        // Initialize Cloudinary with your configuration (if needed)
        val config = HashMap<String, String>()
        config["cloud_name"] = "drykew7pu"
        config["api_key"] = "891342176588327"
        config["api_secret"] = "-7N8kuVvR0FNLLPYFModBB_03UM"

        MediaManager.init(this, config)
    }

}

