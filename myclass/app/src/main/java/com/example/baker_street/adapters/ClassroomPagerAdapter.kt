package com.example.baker_street.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baker_street.fragments.AssignmentsFragment
import com.example.baker_street.fragments.ClassMaterialsFragment
import com.example.baker_street.fragments.PeopleFragment
import com.example.baker_street.fragments.StreamFragment

class ClassroomPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,var courseid :String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        val course_id = this.courseid
        val fragment: Fragment = when (position) {
            0 -> StreamFragment(course_id)
            1 -> ClassMaterialsFragment(course_id)
            2 -> AssignmentsFragment(course_id)
            else -> PeopleFragment()
        }
        return fragment
    }
}