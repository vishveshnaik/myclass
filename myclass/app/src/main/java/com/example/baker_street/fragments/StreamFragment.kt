package com.example.baker_street.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.baker_street.activities.SubAnnouncementActivity
import com.example.baker_street.adapters.StreamFragmentAdapter
import com.example.baker_street.databinding.FragmentStreamBinding
import com.example.baker_street.models.AnnouncementModel
import com.example.baker_street.viewmodels.ClassroomViewModel
import java.io.Serializable

class StreamFragment(var courseid: String) : Fragment(),
    StreamFragmentAdapter.OnAnnouncementClickListener {
    lateinit var binding: FragmentStreamBinding
    private lateinit var adapter: StreamFragmentAdapter
    private lateinit var viewModel: ClassroomViewModel
    private lateinit var token: String
    private var announcementsList = ArrayList<AnnouncementModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStreamBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ClassroomViewModel::class.java]
        val sharedPreferences = context?.getSharedPreferences("Baker_Street", Context.MODE_PRIVATE)
        token = sharedPreferences?.getString("jwtToken", "").toString()
        Log.d("NIKI", courseid)
        viewModel.getAnnouncements(token, courseid)
        binding.revAnnouncements.setHasFixedSize(true)
        adapter = StreamFragmentAdapter()
        binding.revAnnouncements.adapter = adapter

        adapter.setOnAnnouncementListener(this)

        try {
            initObservers()
        }catch (e:Exception){

        }

        return binding.root
    }

    private fun initObservers() {
        viewModel.getMessageObserver()?.observe(viewLifecycleOwner) { message ->
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            if (message == "OKAnnouncements") {
                viewModel.getAnnouncementsObserver()?.observe(viewLifecycleOwner) {
                    Log.d("KIKIk", it.toString())
//                    binding.swipeRefresh.isRefreshing = false
                    announcementsList = if (it == null) {
                        ArrayList()
                    } else {
                        Log.d("sai", it.toString())
                        it.announcementsModel!!
                    }
                    adapter.setList(announcementsList)
                }
            }
        }
    }

    override fun onAnnouncementClick(pos: Int) {
        val intent = Intent(context,SubAnnouncementActivity::class.java)
        intent.putExtra("Announcement",announcementsList[pos] as Serializable)
        startActivity(intent)
    }
}