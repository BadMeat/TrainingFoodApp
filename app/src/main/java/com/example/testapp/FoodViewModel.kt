package com.example.testapp

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.ApiConfig
import com.example.testapp.api.FoodDatabase
import com.example.testapp.model.CategoriesItem
import com.example.testapp.model.ResponseMeal
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodViewModel(application: Application) : BaseViewModel(application) {
    val listFood = MutableLiveData<MutableList<CategoriesItem>>()
    var listPesanan = MutableLiveData<MutableList<CategoriesItem>>()
    val isLoading = MutableLiveData<Boolean>()
    var totalBelanja = MutableLiveData<Int>()

    init {
        listPesanan.value = ArrayList()
        listFood.value = ArrayList()
    }

    // tambah pesanan ketika tombol + dipencet
    fun addPesanan(item: CategoriesItem) {
        launch {
            FoodDatabase(getApplication()).foodDao().insertFood(item)
            listPesanan.value?.add(item)
            listPesanan.value = listPesanan.value
        }
    }

    // menampilkan pesanan yg sudah kita tambah dari  db
    fun loadPesanan() {
        launch {
            val db = FoodDatabase(getApplication()).foodDao().getAll()
            listPesanan.value?.clear()
            listPesanan.value?.addAll(db)
            listPesanan.value = listPesanan.value
        }
    }

    // hapus semua pesanan dari db
    fun hapusPesanan() {
        launch {
            FoodDatabase(getApplication()).foodDao().deleteAll()
            Toast.makeText(getApplication(), "Hapus berhasil", Toast.LENGTH_SHORT).show()
            totalBelanja.value = 0
            loadQuery()
        }
    }

    // hapus 1 pesanan dari makanan yg sudah kita pesan
    fun hapusItem(e: CategoriesItem) {
        launch {
            FoodDatabase(getApplication()).foodDao().deleteById(e.idCategory)
            Toast.makeText(
                getApplication(),
                "Hapus Item ${e.strCategory} Berhasil",
                Toast.LENGTH_SHORT
            )
                .show()
            loadQuery()
            hitungTotalBelanja()
        }
    }

    // load query makanan yg sudah kita pesan
    suspend fun loadQuery() {
        val db = FoodDatabase(getApplication()).foodDao().getAll()
        listPesanan.value?.clear()
        listPesanan.value?.addAll(db)
        listPesanan.value = listPesanan.value
    }

    // hapus daftar makanan yg sudah kita pesan
    fun removeList(item: CategoriesItem) {
        listFood.value?.remove(item)
        listFood.value = listFood.value
    }

    // update jumlah makanan yg sudah kita pesan
    fun updatePesanan(item: CategoriesItem) {
        launch {
            val dbF = FoodDatabase(getApplication()).foodDao()
            val e = dbF.getById(item.idCategory)
            e.jumlah = item.jumlah
            dbF.updateFood(e)

            hitungTotalBelanja()

            loadQuery()
        }
    }

    // hitung keseluruhan makanan yg sudah kita pesan
    fun hitungTotalBelanja() {
        var total = 0
        listPesanan.value?.let {
            for (i in it) {
                total += i.harga * i.jumlah
            }
        }
        totalBelanja.value = total
    }

    // menampilkan data dari api
    fun loadData() {
        isLoading.value = true
        ApiConfig.findMeal().getMeal().enqueue(object : Callback<ResponseMeal> {
            override fun onResponse(call: Call<ResponseMeal>, response: Response<ResponseMeal>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.categories?.let {
                        launch {
                            listFood.value = mutableListOf()
                            listFood.value?.addAll(it)

                            val getAllPesanan = FoodDatabase(getApplication()).foodDao().getAll()
                            listPesanan.value?.addAll(getAllPesanan)

                            // hapus list makanan dari list makanan yg sudah kita pesan
                            val idListPesanan = listPesanan.value?.map { id -> id.idCategory }
                            idListPesanan?.let {
                                listFood.value?.removeAll { tmpFood ->
                                    it.any { it.equals(tmpFood.idCategory, true) }
                                }
                            }

                            listFood.value = listFood.value
                            isLoading.value = false
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMeal>, t: Throwable) {
                t.localizedMessage?.let {
                    Log.d("Saya tidak suka", it)
                }
                isLoading.value = false
            }
        })
    }
}