package com.example.baker_street.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.baker_street.RetroInstance
import com.example.baker_street.models.CommentsModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentRepo {

    private lateinit var instance: CommentRepo
    private val message = MutableLiveData<String>()
    private val commentsModel = MutableLiveData<CommentsModel>()

    fun getInstance(): CommentRepo {
        instance = CommentRepo()
        return instance
    }

    fun getComments(jwtToken: String, sourceid: Any, sourcetype: String) {
        GlobalScope.launch {
            RetroInstance.api.getComments("Bearer $jwtToken", sourceid, sourcetype)
                .enqueue(object : Callback<CommentsModel> {
                    override fun onResponse(
                        call: Call<CommentsModel>,
                        response: Response<CommentsModel>
                    ) {
                        try {
                            commentsModel.postValue(response.body())
                            Log.d("obul", response.toString())
                            message.postValue("OKComments")
                        } catch (E: Exception) {
                            commentsModel.postValue(response.body())
                            Log.d("obul2", E.toString())
                            message.postValue("ErrorComments")
                        }
                    }

                    override fun onFailure(call: Call<CommentsModel>, t: Throwable) {
                        message.postValue(t.toString())
                        Log.d("obul3", t.toString())
                    }
                })
        }
    }

    fun getMessageObserver(): MutableLiveData<String> {
        return message
    }

    fun getCommentsObserver(): MutableLiveData<CommentsModel> {
        return commentsModel
    }
}