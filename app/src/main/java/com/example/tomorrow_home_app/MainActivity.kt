package com.example.tomorrow_home_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.tomorrow_home_app.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSignUpButton()
        setupSignInOutButton()
    }

    override fun onStart() {
        super.onStart()

        if(Firebase.auth.currentUser == null) {
            initViewToSignOutState()
        }else {
            initViewToSignInState()
        }
    }

    private fun setupSignInOutButton() {
        binding.signInOutButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (Firebase.auth.currentUser == null) {
                // 로그인
                if (email.isEmpty() || password.isEmpty()) {
                    Snackbar.make(binding.root, "이메일 또는 패스워드를 입력해주세요.", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                Firebase.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            initViewToSignInState()
                        } else {
                            Snackbar.make(binding.root, "로그인에 실패했습니다. 이메일 또는 패스워드를 확인해주세요.", Snackbar.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Firebase.auth.signOut()
                initViewToSignOutState()

            }
        }
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if(Firebase.auth.currentUser == null) {
                if (email.isEmpty() || password.isEmpty()) {
                    Snackbar.make(binding.root, "이메일 또는 패스워드를 입력해 주세요", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                Firebase.auth.signInWithEmailAndPassword(email , password)
                    .addOnCompleteListener {task ->
                        if(task.isSuccessful) {
                            initViewToSignInState()
                        }else {
                            Snackbar.make(binding.root, "로그인에 실패했습니다. 이메일 또는 패스워드를 확인해주세요.", Snackbar.LENGTH_SHORT).show()
                        }
                    }
            }else {
                // 로그아웃

                Firebase.auth.signOut()
                initViewToSignOutState()

            }
        }
    }


    private fun initViewToSignInState() {
        binding.emailEditText.setText(Firebase.auth.currentUser?.email)
        binding.emailEditText.isEnabled = false
        binding.passwordEditText.isVisible = false
        binding.signInOutButton.text = getString(R.string.signOut)
        binding.signUpButton.isEnabled = false
    }


    private fun initViewToSignOutState() {
        binding.emailEditText.text.clear()
        binding.emailEditText.isEnabled = true
        binding.passwordEditText.text.clear()
        binding.passwordEditText.isVisible = true
        binding.signInOutButton.text = getString(R.string.signIn)
        binding.signUpButton.isEnabled = true
    }
}