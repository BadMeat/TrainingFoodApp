package com.example.testapp.model

import com.google.gson.annotations.SerializedName

data class ResponseMeal(

	@field:SerializedName("categories")
	val categories: List<CategoriesItem>? = null
)