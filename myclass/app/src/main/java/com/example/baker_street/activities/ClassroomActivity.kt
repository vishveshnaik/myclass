package com.example.baker_street.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.baker_street.R
import com.example.baker_street.adapters.ClassroomPagerAdapter
import com.example.baker_street.databinding.ActivityClassroomBinding
import com.google.android.material.tabs.TabLayout

class ClassroomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassroomBinding
    private lateinit var adapter: ClassroomPagerAdapter
    private lateinit var courseid : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassroomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        courseid = intent.getSerializableExtra("courseid").toString()

        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText("Stream")
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText("Class Materials")
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText("Assignments")
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText("People")
        )
        adapter = ClassroomPagerAdapter(supportFragmentManager, lifecycle,courseid)
        binding.viewPager.adapter = adapter

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }
}