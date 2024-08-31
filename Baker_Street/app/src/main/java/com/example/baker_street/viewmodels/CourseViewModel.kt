package com.example.baker_street.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baker_street.models.CoursesModel
import com.example.baker_street.repositories.CourseRepo

class CourseViewModel : ViewModel() {
    private var message: MutableLiveData<String>? = null
    private var courses: MutableLiveData<CoursesModel>? = null
    private var repo: CourseRepo? = null

    init {
        repo = CourseRepo().getInstance()
        message = MutableLiveData<String>()
        courses = MutableLiveData<CoursesModel>()
    }
    fun getCourses(jwtToken: String) {
        repo?.getCourses(jwtToken)
    }

    fun getMessageObserver(): MutableLiveData<String>? {
        message = repo?.getMessageObserver()
        return message
    }
    fun getCoursesObserver(): MutableLiveData<CoursesModel>? {
        courses = repo?.getCoursesObserver()
        return courses
    }
}