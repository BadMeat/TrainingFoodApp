package com.example.testapp.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.BillFragmentDirections
import com.example.testapp.R
import com.example.testapp.databinding.BayarItemBinding
import com.example.testapp.model.BayarModel

class BayarListAdapter(val listBayar: List<BayarModel>) :
    RecyclerView.Adapter<BayarListAdapter.BayarHolder>() {

    class BayarHolder(val binding: BayarItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bayar: BayarModel) {
            Glide.with(binding.root).load(bayar.gambar).error(R.drawable.ic_bill)
                .into(binding.imgBayar)
            itemView.setOnClickListener {
                val action = BillFragmentDirections.actionInvoice(bayar.nama)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BayarHolder {
        val binding = BayarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BayarHolder(binding)
    }

    override fun getItemCount() = listBayar.size

    override fun onBindViewHolder(holder: BayarHolder, position: Int) {
        holder.bind(listBayar[position])
    }
}