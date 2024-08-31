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
import com.example.baker_street.activities.SubAnnouncementActivity
import com.example.baker_street.activities.SubAssignmentActivity
import com.example.baker_street.adapters.AssignmentsFragmentAdapter
import com.example.baker_street.adapters.StreamFragmentAdapter
import com.example.baker_street.databinding.FragmentAssignmentsBinding
import com.example.baker_street.databinding.FragmentStreamBinding
import com.example.baker_street.models.AnnouncementModel
import com.example.baker_street.models.AssignmentModel
import com.example.baker_street.viewmodels.ClassroomViewModel
import java.io.Serializable

class AssignmentsFragment (var courseid: String) : Fragment(),
AssignmentsFragmentAdapter.OnAssignmentClickListener {
    lateinit var binding: FragmentAssignmentsBinding
    private lateinit var adapter: AssignmentsFragmentAdapter
    private lateinit var viewModel: ClassroomViewModel
    private lateinit var token: String
    private var assignmentsList = ArrayList<AssignmentModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssignmentsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ClassroomViewModel::class.java]
        val sharedPreferences = context?.getSharedPreferences("Baker_Street", Context.MODE_PRIVATE)
        token = sharedPreferences?.getString("jwtToken", "").toString()
        Log.d("NIKI", courseid)
        viewModel.getAssignments(token, courseid)
        binding.revAssignments.setHasFixedSize(true)
        adapter = AssignmentsFragmentAdapter()
        binding.revAssignments.adapter = adapter

        adapter.setOnAssignmentListener(this)

        initObservers()

        return binding.root
    }

    private fun initObservers() {
        viewModel.getMessageObserver()?.observe(viewLifecycleOwner) { message ->
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            if (message == "OKAssignments") {
                viewModel.getAssignmentsObserver()?.observe(viewLifecycleOwner) {
                    Log.d("KIKIk", it.toString())
//                    binding.swipeRefresh.isRefreshing = false
                    assignmentsList = if (it == null) {
                        ArrayList()
                    } else {
                        Log.d("sai", it.toString())
                        it.assignments!!
                    }
                    adapter.setList(assignmentsList)
                }
            }
        }
    }

    override fun onAssignmentClick(pos: Int) {
        val intent = Intent(context, SubAssignmentActivity::class.java)
        intent.putExtra("Assignment", assignmentsList[pos] as Serializable)
        startActivity(intent)
    }
}
