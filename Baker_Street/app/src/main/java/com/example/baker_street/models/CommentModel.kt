package com.example.baker_street.models

data class CommentModel(
    val sourceid: Any? = null,
    val sourcetype: String? = null,
    val text: String? = null,
    val publisher_id: String? = null,
    val _id: String? = null,
)

data class CommentsModel(
    val comments: ArrayList<CommentModel>?=null
)
