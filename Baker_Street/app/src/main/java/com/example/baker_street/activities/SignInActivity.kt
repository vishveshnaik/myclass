package com.example.baker_street.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.baker_street.databinding.ActivitySigninBinding
import com.example.baker_street.models.UserModel
import com.example.baker_street.viewmodels.AuthViewModel

class SignInActivity : AppCompatActivity() {


    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var binding: ActivitySigninBinding
    private lateinit var signinviewmodel: AuthViewModel
    private val SPLASH_TIME_OUT = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        signinviewmodel = ViewModelProvider(this)[AuthViewModel::class.java]


        email = binding.email
        password = binding.password

        binding.btnLogin.setOnClickListener {
            signinviewmodel.signIn(
                UserModel(
                    email = email.text.toString(),
                    password = password.text.toString()
                )
            )
        }
       try {
           initObservers()
       }catch (e:Exception){}
        binding.forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.goToSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun initObservers() {
        signinviewmodel.getMessageObserver()?.observe(this) { it ->
            if (it == "OK3") {
                signinviewmodel.getSignInObserver()?.observe(this) {
                    Log.d("Vishnu", it.toString())
                    val sharedPreferences = getSharedPreferences("Baker_Street", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putString("jwtToken", it.jwtToken)
                    editor?.apply()
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignInActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            } else if (it == "Error3") {
                Log.d("Vishnu2", it.toString())
                signinviewmodel.getSignInObserver()?.observe(this) {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.d("Vishnu3", it.toString())
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }



}