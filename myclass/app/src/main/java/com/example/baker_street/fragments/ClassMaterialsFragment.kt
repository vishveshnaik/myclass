package com.example.baker_street.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.baker_street.R
import com.example.baker_street.activities.SubAnnouncementActivity
import com.example.baker_street.adapters.ClassMaterialsFragmentAdapter
import com.example.baker_street.adapters.StreamFragmentAdapter
import com.example.baker_street.databinding.FragmentClassMaterialsBinding
import com.example.baker_street.models.MaterialModel
import com.example.baker_street.viewmodels.ClassroomViewModel
import java.io.Serializable

class ClassMaterialsFragment(var courseid: String) : Fragment() {
    lateinit var binding: FragmentClassMaterialsBinding
    private lateinit var adapter: ClassMaterialsFragmentAdapter
    private lateinit var viewModel: ClassroomViewModel
    private lateinit var token: String
    private var materialsList = ArrayList<MaterialModel>()
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClassMaterialsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ClassroomViewModel::class.java]
        val sharedPreferences = context?.getSharedPreferences("Baker_Street", Context.MODE_PRIVATE)
        token = sharedPreferences?.getString("jwtToken", "").toString()
        viewModel.getClassMaterials(token, courseid)
        binding.revAnnouncements.setHasFixedSize(true)
        adapter = ClassMaterialsFragmentAdapter()
        binding.revAnnouncements.adapter = adapter
        try {
            initObservers()
        }catch (e:Exception){

        }


        return binding.root
    }

    private fun initObservers() {
        viewModel.getMessageObserver()?.observe(viewLifecycleOwner) { message ->
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            if (message == "OKClassMaterials") {
                viewModel.getClassMaterialsObserver()?.observe(viewLifecycleOwner) {
                    Log.d("KIKIk", it.toString())
//                    binding.swipeRefresh.isRefreshing = false
                    materialsList = if (it == null) {
                        ArrayList()
                    } else {
                        Log.d("sai", it.toString())
                        it.materials!!
                    }
                    adapter.setList(materialsList)
                }
            }
        }
    }

}