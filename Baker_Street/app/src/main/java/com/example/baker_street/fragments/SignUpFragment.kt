package com.example.baker_street.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.baker_street.activities.MainActivity
import com.example.baker_street.activities.SignInActivity
import com.example.baker_street.databinding.LayoutSignupBinding
import com.example.baker_street.models.UserModel
import com.example.baker_street.viewmodels.AuthViewModel

class SignUpFragment : Fragment() {

    private lateinit var email: EditText
    private lateinit var name: EditText
    private lateinit var admno: EditText
    private lateinit var password: EditText
    private lateinit var cnfpassword: EditText
    private lateinit var signupviewmodel: AuthViewModel
    private val stuOrProf by lazy { arguments?.getString(STU_OR_PROF) }
    private lateinit var binding: LayoutSignupBinding
    private lateinit var userModel: UserModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutSignupBinding.inflate(layoutInflater)

        email = binding.email
        name = binding.name
        admno = binding.admno
        password = binding.password
        cnfpassword = binding.confirmPassword

        signupviewmodel = ViewModelProvider(this)[AuthViewModel::class.java]

        if (stuOrProf == "PROFESSOR") admno.visibility = View.GONE
        if (stuOrProf == "STUDENT") name.visibility = View.GONE

//        Log.d("UserMOdel enti4",userModel.toString())

        binding.btnSignUp.setOnClickListener {
            userModel = if (stuOrProf == "STUDENT") {
                UserModel(
                    email = email.text.toString(),
                    admno = admno.text.toString(),
                    password = password.text.toString()
                )
            } else {
                UserModel(
                    name = name.text.toString(),
                    email = email.text.toString(),
                    password = password.text.toString()
                )
            }
//            Log.d("chandra",userModel.toString())
            if (password.text.toString() != cnfpassword.text.toString()) {
                Toast.makeText(context, "Passwords are different", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (stuOrProf == "STUDENT") signupviewmodel.signUpStu(userModel)
            else signupviewmodel.signUpProf(userModel)
        }
        initObservers()

        binding.goToLogin.setOnClickListener {
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun initObservers() {
        signupviewmodel.getMessageObserver()?.observe(viewLifecycleOwner) { it ->
            if (it == "OK1") {
                signupviewmodel.getSignUpStuObserver()?.observe(viewLifecycleOwner) {
                    val sharedPreferences =
                        context?.getSharedPreferences("Baker_Street", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    Log.d("okokok",it.toString())
                    editor?.putString("jwtToken", it.jwtToken)
                    editor?.apply()

                    Log.d("NIK1", it.toString())
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }

            } else if (it == "OK2") {
                signupviewmodel.getSignUpProfObserver()?.observe(viewLifecycleOwner) {
                    val sharedPreferences =
                        context?.getSharedPreferences("Baker_Street", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.putString("jwtToken", it.jwtToken)
                    editor?.apply()

                    Log.d("NIK1", it.toString())
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }

            } else if (it == "Error1") {
                signupviewmodel.getSignUpStuObserver()?.observe(viewLifecycleOwner) {
                    Log.d("NIK2", it.message.toString())
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            } else if (it == "Error2") {
                signupviewmodel.getSignUpProfObserver()?.observe(viewLifecycleOwner) {
                    Log.d("NIK2", it.message.toString())
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.d("NIK3", it.toString())
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        var STU_OR_PROF = "stu_or_prof"

        @JvmStatic
        fun newInstance(stu_or_prof: String) = SignUpFragment().apply {
            arguments = Bundle().apply {
                putSerializable(STU_OR_PROF, stu_or_prof)
            }
        }
    }
}