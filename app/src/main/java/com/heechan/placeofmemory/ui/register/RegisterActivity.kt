package com.heechan.placeofmemory.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.heechan.placeofmemory.DesignSystemActivity
import com.heechan.placeofmemory.R
import com.heechan.placeofmemory.base.BaseActivity
import com.heechan.placeofmemory.databinding.ActivityRegisterBinding
import com.heechan.placeofmemory.util.State

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    private val viewModel : RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.state.observe(this) {
            when(it) {
                State.Success -> {
                    val intent = Intent(this, DesignSystemActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                State.Loading -> {

                }
                State.Fail -> {
                    Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.erroeMessage.observe(this) {
            Log.d("registerState", it)
        }


        binding.vm = viewModel
    }
}