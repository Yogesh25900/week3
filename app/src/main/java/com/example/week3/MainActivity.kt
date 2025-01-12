package com.example.week3


import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.week3.R
import com.example.week3.helper.CustomNotificationHelper

class MainActivity : AppCompatActivity() {

    private lateinit var notificationHelper: CustomNotificationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationHelper = CustomNotificationHelper(this)

        // Example button to trigger notification
        val showNotificationButton: Button = findViewById(R.id.button)
        showNotificationButton.setOnClickListener {
            // Show custom notification
            notificationHelper.showCustomNotification(
                "New Custom Notification",
                "This is a custom message for the user.",
                R.drawable.vidgram_logo_top
            )

            Toast.makeText(this, "Notification sent", Toast.LENGTH_SHORT).show()
        }
    }
}
