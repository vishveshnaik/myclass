package com.example.baker_street.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.baker_street.adapters.PeopleFragmentAdapter
import com.example.baker_street.databinding.FragmentPeopleBinding
class PeopleFragment : Fragment() {
    lateinit var binding: FragmentPeopleBinding
    private lateinit var adapter : PeopleFragmentAdapter
//    private lateinit var viewModel : ClassroomViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPeopleBinding.inflate(layoutInflater)
//        viewModel = ViewModelProvider(viewLifecycleOwner)[ClassroomViewModel::class.java]
    try {
        initObservers()
    }catch (e:Exception){

    }
        adapter = PeopleFragmentAdapter()
        binding.revPeople.adapter = adapter

        return binding.root
    }

    private fun initObservers() {

    }
}