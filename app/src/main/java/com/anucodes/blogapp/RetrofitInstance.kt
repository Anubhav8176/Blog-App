package com.anucodes.blogapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private var retrofit: Retrofit? = null

    val instance : Retrofit?
        get() {
            if(retrofit == null){
                retrofit = Retrofit.Builder()
                    .baseUrl("https://terrific-hope-production.up.railway.app/blog/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}