package com.example.week3.UI.activity
import android.content.Intent

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.week3.R
import com.example.week3.databinding.ActivityLoginBinding
import com.example.week3.respository.UserRepositoryImp
import com.example.week3.ui.activity.RegisterActivity
import com.example.week3.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userrepositoryimp = UserRepositoryImp()
        userViewModel= UserViewModel(userrepositoryimp)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            var email =binding.etUsername.text.toString()
            var password = binding.etPassword.text.toString()
            userViewModel.login(email,password){success,message ->
                if(success){
                    val intent = Intent(this,NavActivity::class.java)
                    startActivity(intent)
                    finish()
                    }
            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}