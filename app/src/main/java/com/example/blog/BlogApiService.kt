package com.example.blog

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogApiService {
    @GET("wp/v2/posts")
    suspend fun getBlogPosts(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): List<BlogPost>

    companion object {
        private const val BASE_URL = "https://blog.vrid.in/wp-json/"
        fun create(): BlogApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BlogApiService::class.java)
        }
    }
}