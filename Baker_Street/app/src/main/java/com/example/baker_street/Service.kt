package com.example.baker_street

import com.example.baker_street.models.*
import retrofit2.Call
import retrofit2.http.*

interface Service {

    @POST("auth/signup/students")
    fun signUpStu(
        @Body userModel: UserModel
    ): Call<UserModel>

    @POST("auth/signup/professors")
    fun signUpProf(
        @Body userModel: UserModel
    ): Call<UserModel>

    @POST("auth/login")
    fun signIn(
        @Body userModel: UserModel
    ): Call<UserModel>

    @POST("auth/resetpass")
    fun resetPass(
        @Body userModel: UserModel
    ): Call<UserModel>

    @GET("courses/")
    fun getCourses(
        @Header("Authorization") jwtToken: String
    ): Call<CoursesModel>

    @GET("courses/announcements/{courseid}")
    fun getAnnouncements(
        @Header("Authorization") jwtToken: String,
        @Path("courseid") courseid: String
    ): Call<AnnouncementsModel>

    @GET("courses/files/comments/{sourceid}/{sourcetype}")
    fun getComments(
        @Header("Authorization") jwtToken: String,
        @Path("sourceid") sourceid: Any,
        @Path("sourcetype") sourcetype: String
    ): Call<CommentsModel>

    @GET("courses/materials/{courseid}")
    fun getMaterials(
        @Header("Authorization") jwtToken: String,
        @Path("courseid") courseid: Any
    ): Call<MaterialsModel>

    @POST("courses/materials/{courseid}")
    fun setMaterials(
        @Header("Authorization") jwtToken: String,
        @Path("courseid") courseid: String
    )

    @GET("courses/assignments/{courseid}")
    fun getAssignments(
        @Header("Authorization") jwtToken: String,
        @Path("courseid") courseid: String,
    ) :Call<AssignmentsModel>

    @GET("courses/assignments/submissions/{assignmentid}")
    fun getSubmissions(
        @Header("Authorization") jwtToken: String,
        @Path("courseid") courseid: String,
        @Path("sourcetype") sourcetype: String
    )

    @POST("courses/assignments/submissions/grade")
    fun setGradeAssignments(
        @Header("Authorization") jwtToken: String,
        @Path("courseid") courseid: String,
        @Path("sourcetype") sourcetype: String
    )

}