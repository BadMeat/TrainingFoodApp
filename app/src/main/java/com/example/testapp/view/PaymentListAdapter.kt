package com.example.testapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.R
import com.example.testapp.databinding.BillItemBinding
import com.example.testapp.model.CategoriesItem
import com.example.testapp.util.formatDecimalSeparator

class PaymentListAdapter(
    private val pencetKurang: (CategoriesItem) -> Unit,
    private val pencetTambah: (CategoriesItem) -> Unit,
    private val pencetHapus: (CategoriesItem) -> Unit
) : RecyclerView.Adapter<PaymentListAdapter.FoodViewHolder>() {
    private val foodList = mutableListOf<CategoriesItem>()

    fun setFoodList(e: List<CategoriesItem>) {
        foodList.clear()
        foodList.addAll(e)
        notifyDataSetChanged()
    }

    class FoodViewHolder(private val binding: BillItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            e: CategoriesItem, pencetKurang: (CategoriesItem) -> Unit,
            pencetTambah: (CategoriesItem) -> Unit, pencetHapus: (CategoriesItem) -> Unit
        ) {
            binding.apply {
                txtTitle.text = e.strCategory
                Glide.with(binding.root).load(e.strCategoryThumb).error(R.drawable.ic_bill)
                    .into(imgProfile)
                txtJumlah.text = e.jumlah.toString()
                val formatRupiah = e.harga.formatDecimalSeparator()
                txtDesc.text = itemView.context.getString(R.string.format_rupiah, formatRupiah)
                btnKurang.setOnClickListener {
                    pencetKurang(e)
                }
                btnTambah.setOnClickListener {
                    pencetTambah(e)
                }
                btnHapus.setOnClickListener {
                    pencetHapus(e)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding =
            BillItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount() = foodList.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position], pencetKurang, pencetTambah, pencetHapus)
    }
}