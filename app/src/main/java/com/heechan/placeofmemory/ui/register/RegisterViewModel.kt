package com.heechan.placeofmemory.ui.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseApp
import com.heechan.placeofmemory.model.data.User
import com.heechan.placeofmemory.model.remote.AuthRepositoryImpl
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val repository = AuthRepositoryImpl()

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordRe = MutableLiveData<String>()

    fun register() {
        viewModelScope.launch {
            Log.d("registerResult", "로그인 시작")

            val userData = User(name = "박희찬", email = "ckstmznf@naver.com")
            val password = "qwer1234"
            val result = repository.register(userData = userData, password = password)

            Log.d("registerResult", result.toString())
        }
    }
}