package com.example.testapp.api

import com.example.testapp.model.ResponseMeal
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("JSONDUmmy/main/data.json")
    fun getMeal() : Call<ResponseMeal>
}