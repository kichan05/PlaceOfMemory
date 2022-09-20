package com.heechan.placeofmemory.model.remote

import android.media.session.MediaSessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.heechan.placeofmemory.model.data.User
import com.heechan.placeofmemory.util.FireebaseCollection
import com.heechan.placeofmemory.util.Result
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {
    companion object {
        private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    }

    override suspend fun register(userData: User, password: String): Result {
        val saveUserDataResult =  saveUserData(userData = userData)
        if(saveUserDataResult == Result.fail)
            return saveUserDataResult

        val createUserResult = createUser(userData = userData, password = password)
        return createUserResult
    }

    override suspend fun createUser(userData: User, password: String): Result {
        var result : Result = Result.fail
        auth.createUserWithEmailAndPassword(userData.email, password)
            .addOnSuccessListener {
                result = Result.success
            }
            .await()

        return result
    }

    override suspend fun saveUserData(userData: User): Result {
        var result : Result = Result.fail

        db.collection(FireebaseCollection.auth)
            .document(userData.uuid)
            .set(userData)
            .addOnSuccessListener {
                result = Result.success
            }

        return result
    }

    override suspend fun login(email: String, password: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getUserDataByEmail(email: String): User? {
        TODO("Not yet implemented")
    }
}