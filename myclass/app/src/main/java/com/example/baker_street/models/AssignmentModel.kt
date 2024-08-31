package com.example.baker_street.models

data class AssignmentModel(
    val _id: String? = null,
    val url: String? = null,
    val text: String? = null,
    val courseid: String? = null,
    val createdAt: String? = null
):java.io.Serializable

data class AssignmentsModel(
    val assignments: ArrayList<AssignmentModel>? = null
)
