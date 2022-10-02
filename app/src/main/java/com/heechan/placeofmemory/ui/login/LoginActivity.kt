package com.heechan.placeofmemory.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.heechan.placeofmemory.DesignSystemActivity
import com.heechan.placeofmemory.R
import com.heechan.placeofmemory.base.BaseActivity
import com.heechan.placeofmemory.databinding.ActivityLoginBinding
import com.heechan.placeofmemory.ui.main.MainActivity
import com.heechan.placeofmemory.util.State

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var client: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.vm = viewModel

        val option = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        client = GoogleSignIn.getClient(this, option)

        binding.btnLoginGoogleLogin.setOnClickListener {
            startActivityForResult(client.signInIntent, 1)
        }

        /*
        viewModel.state.observe(this) {
            when (it) {
                State.Success -> {
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()

                    val intent: Intent = Intent(this, DesignSystemActivity::class.java).apply {
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
         */
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("googleLogin", "재시작")

        when (requestCode) {
            1 -> {
                Log.d("googleLogin", requestCode.toString())
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)

                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                Toast.makeText(this, "환영합니다", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "로그인에 실패 했습니다", Toast.LENGTH_SHORT).show()
            }
    }
}