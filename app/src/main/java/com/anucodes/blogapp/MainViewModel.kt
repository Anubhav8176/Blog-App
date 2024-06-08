package com.anucodes.blogapp

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.anucodes.blogapp.model.Blog
import kotlinx.coroutines.*
import retrofit2.*

class MainViewModel: ViewModel() {
    var blogs by mutableStateOf<List<Blog>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchBlogs()
    }

    fun deleteBlog(id: Int) {
        val apiService = RetrofitInstance.instance?.create(APIService::class.java)
        viewModelScope.launch {Dispatchers.IO}
            val call = apiService?.delete(id)
            call?.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Log.i("DeleteBlog", "Successfully deleted blog with id $id")
                    } else {
                        Log.e("DeleteBlog", "Failed to delete blog with id $id: HTTP ${response.code()} - ${response.message()}")
                        // Log response body if possible
                        response.errorBody()?.let { errorBody ->
                            Log.e("DeleteBlog", "Error body: ${errorBody.string()}")
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("DeleteBlog", "Error deleting blog with id $id", t)
                }
            })

    }



    fun postData(blog: Blog){
        val apiService = RetrofitInstance.instance?.create(APIService::class.java)
        viewModelScope.launch {
            val call = apiService?.add(blog)
            call?.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.i("The response is ", response.body().toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("There is the error in post method", t.toString())
                }


            })
        }
    }

    private fun fetchBlogs() {
        val apiService = RetrofitInstance.instance?.create(APIService::class.java)
        viewModelScope.launch { Dispatchers.IO }
        val call = apiService?.getAll()
        call?.enqueue(object: Callback<List<Blog>>{
            override fun onResponse(call: Call<List<Blog>>, response: Response<List<Blog>>) {
                if(response.isSuccessful){
                    blogs = response.body()!!
                    Log.i("The response is ", response.body().toString())
                }
                else{
                    Log.i("The response is ", response.toString())
                }
            }

            override fun onFailure(call: Call<List<Blog>>, t: Throwable) {
                Log.i("There is the error in get method", t.toString())
            }

        })
    }
}