package com.example.week3.viewmodel

import com.example.week3.model.UserModel
import com.example.week3.respository.UserRepository

class UserViewModel (val repo: UserRepository){

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit){

        repo.login(email,password,callback)
    }
    fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit){

        repo.register(email,password,callback)
    }

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email,callback)
    }

    fun addUserToDatabase(
        userId: String, userModel: UserModel,
        callback: (Boolean, String) -> Unit
    ){
        repo.addUserToDatabase(userId,userModel,callback)
    }
}