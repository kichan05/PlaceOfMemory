package com.heechan.placeofmemory.model.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.heechan.placeofmemory.model.data.User
import com.heechan.placeofmemory.util.FirebaseCollection
import com.heechan.placeofmemory.util.Result
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun register(userData: User, password: String): Result {
//        Log.d("registerResult", "총괄 함수 시작")

        val saveUserDataResult =  saveUserData(userData = userData)
        if(saveUserDataResult == Result.fail)
            return saveUserDataResult

        val createUserResult = createUser(userData = userData, password = password)
//        Log.d("registerResult", "총괄 함수 끝")

        return createUserResult
    }

    override suspend fun createUser(userData: User, password: String): Result {
//        Log.d("registerResult", "계정 생성 시작")
        var result : Result = Result.fail
        auth.createUserWithEmailAndPassword(userData.email, password)
            .addOnSuccessListener {
                result = Result.success
            }
            .await()

//        Log.d("registerResult", "계정 생성 끝 $result")
        return result
    }

    override suspend fun saveUserData(userData: User): Result {
//        Log.d("registerResult", "정보 저장 함수 시작")
        var result : Result = Result.fail

        db.collection(FirebaseCollection.auth)
            .document(userData.email)
            .set(userData)
            .addOnSuccessListener {
                result = Result.success
            }
            .await()

//        Log.d("registerResult", "정보 저장 함수 끝 $result")
        return result
    }

    override suspend fun login(email: String, password: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getUserDataByEmail(email: String): User? {
        TODO("Not yet implemented")
    }
}