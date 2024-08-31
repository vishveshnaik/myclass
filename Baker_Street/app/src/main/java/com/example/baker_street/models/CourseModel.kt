package com.example.baker_street.models

import com.google.gson.annotations.SerializedName

data class CoursesModel(

    val status: String? = null,

    @SerializedName("courses")
    val courses: ArrayList<CourseModel>? = null
)

data class CourseModel(

    val _id: Any? = null,

    val code: String? = null,

    val name: String? = null,

    val profid: String? = null,

    val profname: String? = null,

    val materials: ArrayList<MaterialsModel>? = null,
)

