package com.example.baker_street.activities

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.baker_street.R
import com.example.baker_street.databinding.ActivityprofcourseBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class ProfCourseActivity : AppCompatActivity() {
    private lateinit var binding:ActivityprofcourseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.baker_street.R.layout.activityprofcourse)
        binding = ActivityprofcourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnToAddCourse.setOnClickListener{
            showBottomSheetDialog();
        }
    }
    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.activity_bottom_sheet)
//        val course = bottomSheetDialog.findViewById(com.example.baker_street.R.id.courseName)
//        val share = bottomSheetDialog.findViewById<LinearLayout>(com.example.baker_street.R.id.ButtonAdd)
        bottomSheetDialog.show()
    }

}
