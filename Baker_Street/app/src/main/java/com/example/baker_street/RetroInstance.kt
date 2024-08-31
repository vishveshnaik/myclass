package com.example.baker_street

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {

    private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request()
        request.newBuilder()
            .method(request.method,request.body)
            .build()
        chain.proceed(request)
    }.build()


    private const val URL: String = "https://parishram-backend.vercel.app/"
    private val retroinstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: Service by lazy {
        retroinstance.create(Service::class.java)
    }
}