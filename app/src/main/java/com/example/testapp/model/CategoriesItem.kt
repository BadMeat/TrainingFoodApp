package com.example.testapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CategoriesItem(


    @ColumnInfo(name = "str_category")
    @field:SerializedName("strCategory")
    val strCategory: String? = null,

    @ColumnInfo(name = "str_description")
    @field:SerializedName("strCategoryDescription")
    val strCategoryDescription: String? = null,

    @PrimaryKey
    @ColumnInfo(name = "id_category")
    @field:SerializedName("idCategory")
    val idCategory: String,

    @ColumnInfo(name = "str_categoryThumb")
    @field:SerializedName("strCategoryThumb")
    val strCategoryThumb: String? = null,

    @ColumnInfo(name = "harga")
    @field:SerializedName("harga")
    var harga: Int = 10000,

    @ColumnInfo(name = "jumlah")
    @field:SerializedName("jumlah")
    var jumlah: Int = 5,
)