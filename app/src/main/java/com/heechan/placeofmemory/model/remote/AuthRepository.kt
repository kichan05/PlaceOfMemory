package com.heechan.placeofmemory.model.remote

import com.heechan.placeofmemory.model.data.User
import com.heechan.placeofmemory.util.Result

interface AuthRepository {
    suspend fun register(userData : User, password : String) : Result
    suspend fun createUser(userData : User, password : String) : Result
    suspend fun saveUserData(userData : User) : Result

    suspend fun login(email : String, password: String) : User?
    suspend fun getUserDataByEmail(email: String) : User?
}