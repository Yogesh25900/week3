package com.example.week3.UI.activity

import com.example.week3.R


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var sendResetLinkButton: Button
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        // Initialize the views
        emailEditText = findViewById(R.id.emailEditText)
        emailLayout = findViewById(R.id.emailLayout)
        sendResetLinkButton = findViewById(R.id.sendResetLinkButton)
        statusText = findViewById(R.id.statusText)

        sendResetLinkButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            // Check if email is empty
            if (email.isEmpty()) {
                statusText.text = "Please enter your email."
                return@setOnClickListener
            }

            // Check if email is valid
            if (!isValidEmail(email)) {
                statusText.text = "Invalid email format."
                return@setOnClickListener
            }

            // Trigger the password reset functionality (mocked)
            resetPassword(email)
        }
    }

    // Function to validate email format
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Mocked function to handle password reset process (you should implement actual logic)
    private fun resetPassword(email: String) {
        // You can implement the email sending logic here (e.g., Firebase Auth, SMTP, etc.)
        // For now, we'll show a mock response.

        // Mock success response
        val isSuccess = true  // Change this based on your logic

        if (isSuccess) {
            statusText.text = "A reset link has been sent to your email."
            statusText.setTextColor(resources.getColor(android.R.color.holo_green_dark))
        } else {
            statusText.text = "Failed to send reset link."
            statusText.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        }

        // Optionally, you can show a Toast message for success/failure
        Toast.makeText(this, if (isSuccess) "Check your inbox!" else "Please try again.", Toast.LENGTH_LONG).show()
    }
}
