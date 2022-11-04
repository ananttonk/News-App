package com.example.newsapi

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=324c7ea128ad43c2b79dd0822a26dafa

object Network {

    const val API_KEY="324c7ea128ad43c2b79dd0822a26dafa"
    const val BASE_URL="https://newsapi.org/"

    val API = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build().create(News::class.java)

    interface News {
        @GET("v2/everything?domains=wsj.com")
        fun getNewsList(@Query("apiKey") key: String = API_KEY): Call<NewsList>
    }
}