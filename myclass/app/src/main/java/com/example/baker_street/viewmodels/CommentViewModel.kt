package com.example.baker_street.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baker_street.models.CommentsModel
import com.example.baker_street.repositories.CommentRepo

class CommentViewModel : ViewModel() {
    private var message: MutableLiveData<String>? = null
    private var comments: MutableLiveData<CommentsModel>? = null
    private var repo: CommentRepo? = null

    init {
        repo = CommentRepo().getInstance()
        message = MutableLiveData<String>()
        comments = MutableLiveData<CommentsModel>()
    }

    fun getComments(jwtToken: String, sourceid: Any, sourcetype: String) {
        repo?.getComments(jwtToken, sourceid, sourcetype)
    }

    fun getMessageObserver(): MutableLiveData<String>? {
        message = repo?.getMessageObserver()
        return message
    }

    fun getCommentsObserver(): MutableLiveData<CommentsModel>? {
        comments = repo?.getCommentsObserver()
        return comments
    }

}