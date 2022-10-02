package com.heechan.placeofmemory.ui.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heechan.placeofmemory.model.data.User
import com.heechan.placeofmemory.model.remote.AuthRepositoryImpl
import com.heechan.placeofmemory.util.Result
import com.heechan.placeofmemory.util.State
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val repository = AuthRepositoryImpl()

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordRe = MutableLiveData<String>()
    val state = MutableLiveData<State>()
    val erroeMessage = MutableLiveData<String>()

    fun register() {
        if(!inputCheck()){
            Log.d("registerState", "입력 값 오류")
            return
        }

        viewModelScope.launch {
            val userData = User(name = name.value!!, email = email.value!!)
            val password = this@RegisterViewModel.password.value!!
            val result = repository.register(userData = userData, password = password)

            if(result == Result.Success){
                state.value = State.Success
            }
            else {
               state.value = State.Fail
            }
        }
    }

    private fun inputCheck() : Boolean {
        if(name.value.isNullOrBlank ()){
            erroeMessage.value = "이름을 입력 해주세요."
            return false
        }

        if(email.value.isNullOrBlank()){
            erroeMessage.value = "이메일을 입력 해주세요."
            return false
        }

        if(password.value.isNullOrBlank()){
            erroeMessage.value = "비밀번호를 입력 해주세요."
            return false
        }

        if(passwordRe.value.isNullOrBlank()){
            erroeMessage.value = "비밀번호를 다시 입력 해주세요."
            return false
        }

        if(password.value != passwordRe.value){
            erroeMessage.value = "비밀번호가 서로 다릅니다"
            return false
        }

        return true
    }
}