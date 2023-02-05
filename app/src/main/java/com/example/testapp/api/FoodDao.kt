package com.example.testapp.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.testapp.model.CategoriesItem

@Dao
interface FoodDao {
    @Insert
    suspend fun insertFood(categoriesItem: CategoriesItem)

    @Update
    suspend fun updateFood(categoriesItem: CategoriesItem)

    @Query("select * from CategoriesItem")
    suspend fun getAll(): List<CategoriesItem>

    @Query("select * from CategoriesItem where id_category =:id")
    suspend fun getById(id: String): CategoriesItem

    @Query("delete from CategoriesItem")
    suspend fun deleteAll()

    @Query("delete from CategoriesItem where id_category=:id")
    suspend fun deleteById(id: String)
}