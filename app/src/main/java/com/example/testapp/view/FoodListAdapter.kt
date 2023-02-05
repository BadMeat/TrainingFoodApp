package com.example.testapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.R
import com.example.testapp.databinding.FoodItemBinding
import com.example.testapp.model.CategoriesItem

class FoodListAdapter(private val buttonTambah: (CategoriesItem) -> Unit) :
    RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    private val foodList = mutableListOf<CategoriesItem>()

    fun setFoodList(e: List<CategoriesItem>) {
        foodList.clear()
        foodList.addAll(e)
        notifyDataSetChanged()
    }

    class FoodViewHolder(private val binding: FoodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(e: CategoriesItem, onPencet: (CategoriesItem) -> Unit) {
            binding.apply {
                binding.apply {
                    txtTitle.text = e.strCategory
                    txtDesc.text = e.strCategoryDescription
                    Glide.with(binding.root).load(e.strCategoryThumb).error(R.drawable.ic_bill)
                        .into(imgProfile)
                    btnTambah.setOnClickListener {
                        onPencet(e)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding =
            FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount() = foodList.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position], buttonTambah)
    }
}