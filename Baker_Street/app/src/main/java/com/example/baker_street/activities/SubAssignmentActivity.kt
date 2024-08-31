package com.example.baker_street.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.baker_street.adapters.CommentsAdapter
import com.example.baker_street.databinding.SubItemAnnouncementsBinding
import com.example.baker_street.databinding.SubItemAssignmentsBinding
import com.example.baker_street.models.AnnouncementModel
import com.example.baker_street.models.AssignmentModel
import com.example.baker_street.models.CommentModel
import com.example.baker_street.viewmodels.CommentViewModel

class SubAssignmentActivity : AppCompatActivity() {

    private lateinit var binding: SubItemAssignmentsBinding
    private lateinit var viewModel: CommentViewModel
    private lateinit var adapter: CommentsAdapter
    private lateinit var token: String
    private var commentsList = ArrayList<CommentModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SubItemAssignmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val assignment = intent.getSerializableExtra("Assignment") as AssignmentModel
        viewModel = ViewModelProvider(this)[CommentViewModel::class.java]
        binding.announcement.text = assignment.text
        binding.profName.text = assignment.courseid

        val sharedPreferences = getSharedPreferences("Baker_Street", Context.MODE_PRIVATE)
        token = sharedPreferences?.getString("jwtToken", "").toString()

        binding.revComments.setHasFixedSize(true)
        adapter = CommentsAdapter()
        binding.revComments.adapter = adapter
        //setList
        assignment._id?.let { viewModel.getComments(token, it, "assignments") }
        initObservers()
    }

    private fun initObservers() {
        viewModel.getMessageObserver()?.observe(this) { message ->
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            if (message == "OKComments") {
                viewModel.getCommentsObserver()?.observe(this) {
                    Log.d("KIKIk", it.toString())
//                    binding.swipeRefresh.isRefreshing = false
                    commentsList = if (it == null) {
                        ArrayList()
                    } else {
                        Log.d("sai", it.toString())
                        it.comments!!
                    }
                    adapter.setList(commentsList)
                }
            }
        }
    }
}