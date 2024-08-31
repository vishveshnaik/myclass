package com.example.baker_street.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.baker_street.RetroInstance
import com.example.baker_street.models.CoursesModel
import com.example.baker_street.models.UserModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseRepo {
    private lateinit var instance: CourseRepo
    private val message = MutableLiveData<String>()
    private val coursesModel = MutableLiveData<CoursesModel>()

    fun getInstance(): CourseRepo {
        instance = CourseRepo()
        return instance
    }

    fun getCourses(jwtToken: String) {
        Log.d("qwertyuiop",jwtToken)
        GlobalScope.launch {
            RetroInstance.api.getCourses("Bearer $jwtToken")
                .enqueue(object : Callback<CoursesModel> {
                    override fun onResponse(
                        call: Call<CoursesModel>,
                        response: Response<CoursesModel>
                    ) {
                        try {
                            coursesModel.postValue(response.body())
                            Log.d("qwerty1",response.toString())
                            message.postValue("OKCourses")
                        } catch (E: Exception) {
                            coursesModel.postValue(response.body())
                            Log.d("qwerty2",E.toString())
                            message.postValue("ErrorCourses")
                        }
                    }

                    override fun onFailure(call: Call<CoursesModel>, t: Throwable) {
                        message.postValue(t.toString())
                        Log.d("qwerty3", t.toString())
                    }
                })
        }
    }
    fun getMessageObserver(): MutableLiveData<String> {
        return message
    }
    fun getCoursesObserver(): MutableLiveData<CoursesModel> {
        return coursesModel
    }
}