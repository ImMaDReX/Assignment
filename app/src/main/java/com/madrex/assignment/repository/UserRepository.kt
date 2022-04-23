package com.madrex.assignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.madrex.assignment.model.User
import com.madrex.assignment.retrofit.UserApi
import com.madrex.assignment.roomDb.UserDB
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi, private val userDB: UserDB){
    private val usersMLD = MutableLiveData<List<User>>()
    val usersLD : LiveData<List<User>>
        get() = usersMLD
    suspend fun updateUserTable() {
        val result = userApi.getUsers()
        if(result.isSuccessful && result.body() != null){
            usersMLD.postValue(result.body())
            userDB.getUserDao().addUsers(result.body()!!)
        } else {
            usersMLD.postValue(listOf())
        }

    }
    suspend fun getUsers(){
        val tmp:List<User> = userDB.getUserDao().getUsers()
        usersMLD.postValue(tmp)
    }
}