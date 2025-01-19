package com.example.week3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.week3.model.UserModel
import com.example.week3.respository.UserRepository
import com.google.firebase.auth.FirebaseUser

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

    fun getCurrentUser(): FirebaseUser?{
        return repo.getCurrentUser()
    }

    var _userData = MutableLiveData<UserModel?>()

    var userdata =MutableLiveData<UserModel?>()

        get() = _userData
    fun getUserFromDatabase(userID: String) {
        repo.getUserfromdatabase(userID) { user, success, message ->
            if (success) {
                _userData.value = user
            } else {

                println("Error: $message")
            }
        }
    }



}