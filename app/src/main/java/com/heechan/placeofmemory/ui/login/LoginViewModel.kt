package com.heechan.placeofmemory.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.heechan.placeofmemory.model.data.User
import com.heechan.placeofmemory.model.remote.AuthRepositoryImpl
import com.heechan.placeofmemory.util.State
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    private val repository = AuthRepositoryImpl()

    val state = MutableLiveData<State>()
    val resultUserData = MutableLiveData<User>()

    /*
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    fun login() {
        if(!isInputCheck()){
            return
        }

        viewModelScope.launch(CoroutineExceptionHandler{ a, e ->
            Log.d("loginState", e.toString())

            state.value = State.Fail
        }) {
            state.value = State.Loading

            val result =  withContext(Dispatchers.IO) {
                repository.login(email = email.value!!, password = password.value!!)
            }

            Log.d("loginResult", result.toString())

            if(result == null){
                state.value = State.Fail
            }
            else {
                resultUserData.value = result
                state.value = State.Success
            }
        }
    }
    private fun isInputCheck() : Boolean {
        if(email.value.isNullOrBlank()){
            return false
        }
        if(password.value.isNullOrBlank()){
            return false
        }

        return true
    }
     */
}