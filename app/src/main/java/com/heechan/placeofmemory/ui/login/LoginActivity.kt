package com.heechan.placeofmemory.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.heechan.placeofmemory.DesignSystemActivity
import com.heechan.placeofmemory.R
import com.heechan.placeofmemory.base.BaseActivity
import com.heechan.placeofmemory.databinding.ActivityLoginBinding
import com.heechan.placeofmemory.util.State

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel

        viewModel.state.observe(this) {
            when(it) {
                State.Success -> {
                   Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()

                    val intent : Intent = Intent(this, DesignSystemActivity::class.java).apply {
                        putExtra("userData", viewModel.resultUserData.value!!)
                    }
                    startActivity(intent)
                    finish()
                }
                State.Loading -> {

                }
                State.Fail -> {
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}