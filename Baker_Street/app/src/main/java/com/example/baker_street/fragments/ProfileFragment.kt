package com.example.baker_street.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.baker_street.R
import com.example.baker_street.activities.SignInActivity
import com.example.baker_street.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        binding.signOut.setOnClickListener {
            val sharedPreferences = context?.getSharedPreferences("Baker_Street",Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.clear()
            editor?.apply()
            val intent = Intent(context,SignInActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        // Update the activity's title for this fragment
        activity?.title = "Profile"
    }
}