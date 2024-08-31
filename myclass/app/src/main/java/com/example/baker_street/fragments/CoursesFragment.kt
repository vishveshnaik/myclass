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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.baker_street.activities.ClassroomActivity
import com.example.baker_street.adapters.CoursesAdapter
import com.example.baker_street.databinding.FragmentCoursesBinding
import com.example.baker_street.models.CourseModel
import com.example.baker_street.viewmodels.CourseViewModel
import java.io.Serializable

class CoursesFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    CoursesAdapter.OnCourseClickListener {
    private lateinit var binding: FragmentCoursesBinding
    private lateinit var adapter: CoursesAdapter
    private lateinit var viewModel: CourseViewModel
    private lateinit var token: String
    private var coursesList = ArrayList<CourseModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoursesBinding.inflate(layoutInflater)
        binding.recyclerView.setHasFixedSize(true)
        binding.swipeRefresh.setOnRefreshListener(this)


        viewModel = ViewModelProvider(this)[CourseViewModel::class.java]
        val sharedPreferences = context?.getSharedPreferences("Baker_Street", Context.MODE_PRIVATE)
        token = sharedPreferences?.getString("jwtToken", "").toString()
        viewModel.getCourses(token)
        adapter = CoursesAdapter()
        adapter.setOnCourseListener(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
        try {
            initObservers()
        }catch (e:Exception){

        }
        return binding.root
    }

    private fun initObservers() {
        viewModel.getMessageObserver()?.observe(viewLifecycleOwner) { message ->
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            if (message == "OKCourses") {
                viewModel.getCoursesObserver()?.observe(viewLifecycleOwner) {
                    binding.swipeRefresh.isRefreshing = false
                    if (it == null) {
                        adapter.setList(ArrayList())
                    } else {
                        Log.d("sai", it.toString())
                        coursesList = it.courses!!
                        adapter.setList(coursesList)
                    }
                }
            }
        }
    }

    override fun onRefresh() {
        viewModel.getCourses(token)
    }

    override fun onResume() {
        super.onResume()
        // Update the activity's title for this fragment
        activity?.title = "Courses"
    }

    override fun onCourseClick(pos: Int) {
        val intent = Intent(context, ClassroomActivity::class.java)
        intent.putExtra("courseid", coursesList[pos]._id as Serializable)
        startActivity(intent)
    }
}