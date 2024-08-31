package com.example.baker_street.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.baker_street.RetroInstance
import com.example.baker_street.models.UserModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepo {

    private lateinit var instance: AuthRepo
    private val message = MutableLiveData<String>()
    private val signUpStuModel = MutableLiveData<UserModel>()
    private val signUpProfModel = MutableLiveData<UserModel>()
    private val signInModel = MutableLiveData<UserModel>()

    fun getInstance(): AuthRepo {
        instance = AuthRepo()
        return instance
    }

    fun signUpStu(userModel: UserModel) {
        Log.d("UserMOdel enti",userModel.toString())
        GlobalScope.launch {
            RetroInstance.api.signUpStu(userModel)
                .enqueue(object : Callback<UserModel> {
                    override fun onResponse(
                        call: Call<UserModel>,
                        response: Response<UserModel>
                    ) {
                        try {
                            Log.d("abhilash",response.body()?.message.toString())
                            signUpStuModel.postValue(response.body())
                            message.postValue("OK1")
                        } catch (E: Exception) {
                            Log.d("abhilash2",E.toString())
                            signUpStuModel.postValue(response.body())
                            message.postValue("Error1")
                        }
                    }

                    override fun onFailure(call: Call<UserModel>, t: Throwable) {
                        message.postValue(t.toString())
                        Log.d("abhilash3", t.toString())
                    }
                })
        }
    }

    fun signUpProf(userModel: UserModel) {
        GlobalScope.launch {
            RetroInstance.api.signUpProf(userModel)
                .enqueue(object : Callback<UserModel> {
                    override fun onResponse(
                        call: Call<UserModel>,
                        response: Response<UserModel>
                    ) {
                        try {
                            signUpProfModel.postValue(response.body())
                            message.postValue("OK2")
                            Log.d("Chandra4",response.body().toString())
                        } catch (E: Exception) {
                            signUpProfModel.postValue(response.body())
                            message.postValue("Error2")
                            Log.d("Chandra2",E.toString())
                        }
                    }

                    override fun onFailure(call: Call<UserModel>, t: Throwable) {
                        message.postValue(t.toString())
                        Log.d("Chandra3", t.toString())
                    }
                })
        }
    }

    fun signIn(userModel: UserModel) {
        Log.d("JIO",userModel.toString())
        GlobalScope.launch {
            RetroInstance.api.signIn(userModel)
                .enqueue(object : Callback<UserModel> {
                    override fun onResponse(
                        call: Call<UserModel>,
                        response: Response<UserModel>
                    ) {
                        try {
                            signInModel.postValue(response.body())
                            Log.d("Roop",response.body().toString())
                            message.postValue("OK3")
                        } catch (E: Exception) {
                            Log.d("Roop2",E.toString())
                            signInModel.postValue(response.body())
                            message.postValue("Error3")
                        }
                    }

                    override fun onFailure(call: Call<UserModel>, t: Throwable) {
                        message.postValue(t.toString())
                        Log.d("Roop3", t.toString())
                    }
                })
        }
    }

    fun getMessageObserver(): MutableLiveData<String> {
        return message
    }
    fun getSignUpStuObserver(): MutableLiveData<UserModel> {
        return signUpStuModel
    }
    fun getSignUpProfObserver(): MutableLiveData<UserModel> {
        return signUpProfModel
    }
    fun getSignInObserver(): MutableLiveData<UserModel> {
        return signInModel
    }
}