package com.example.baker_street.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baker_street.R
import com.example.baker_street.databinding.ActivityForgotpasswordBinding

class ForgotPasswordActivity : AppCompatActivity(){
    lateinit var binding : ActivityForgotpasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}