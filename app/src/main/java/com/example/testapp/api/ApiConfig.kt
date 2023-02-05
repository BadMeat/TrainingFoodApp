package com.example.testapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiConfig {
    private const val baseUrl = "https://raw.githubusercontent.com/BadMeat/"

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun findMeal(): ApiService {
        return retrofit().create(ApiService::class.java)
    }
}