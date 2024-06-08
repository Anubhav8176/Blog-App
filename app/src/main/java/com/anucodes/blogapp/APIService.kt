package com.anucodes.blogapp

import com.anucodes.blogapp.model.Blog
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    @GET("all/")
    fun getAll(): Call<List<Blog>>

    @POST("add/")
    fun add(@Body blog: Blog): Call<String>

    @DELETE("delete/{id}/")
    fun delete(@Path("id") id: Int): Call<String>

    @GET("{id}/")
    fun get(@Path("id") id: Int): Call<Blog>


}