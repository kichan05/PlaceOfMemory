package com.heechan.placeofmemory.model.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.heechan.placeofmemory.model.data.User
import com.heechan.placeofmemory.util.FirebaseCollection
import com.heechan.placeofmemory.util.Result
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun register(userData: User, password: String): Result {
        val saveUserDataResult = saveUserData(userData = userData)
        if (saveUserDataResult == Result.Fail)
            return saveUserDataResult

        val createUserResult = createUser(userData = userData, password = password)

        return createUserResult
    }

    override suspend fun createUser(userData: User, password: String): Result {
        var result: Result = Result.Fail
        auth.createUserWithEmailAndPassword(userData.email, password)
            .addOnSuccessListener {
                result = Result.Success
            }
            .await()

        return result
    }

    override suspend fun saveUserData(userData: User): Result {
        var result: Result = Result.Fail

        db.collection(FirebaseCollection.auth)
            .document(userData.email)
            .set(userData)
            .addOnSuccessListener {
                result = Result.Success
            }
            .await()

        return result
    }

    override suspend fun login(email: String, password: String): User? {
        var result : User? = getUserDataByEmail(email) ?: return null

        auth.signInWithEmailAndPassword(email, password)
            .addOnFailureListener {
                result = null
            }
            .await()

        return result
    }

    override suspend fun getUserDataByEmail(email: String): User? {
        var result : User? = null

        val userCol = db.collection(FirebaseCollection.auth)
            .document(email)
            .get()
            .await()

        result = userCol.toObject(User::class.java)

        return result
    }
}