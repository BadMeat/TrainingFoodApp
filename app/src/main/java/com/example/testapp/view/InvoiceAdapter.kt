package com.example.testapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.databinding.InvoiceItemBinding
import com.example.testapp.model.CategoriesItem
import com.example.testapp.util.formatDecimalSeparator

class InvoiceAdapter : RecyclerView.Adapter<InvoiceAdapter.InvoiceHolder>() {

    private val listInvoice = mutableListOf<CategoriesItem>()

    fun setInvoice(e: List<CategoriesItem>) {
        listInvoice.clear()
        listInvoice.addAll(e)
        notifyDataSetChanged()
    }

    class InvoiceHolder(val binding: InvoiceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(e: CategoriesItem) {
            binding.apply {
                txtMakanan.text =
                    itemView.context.getString(R.string.total_makanan, e.strCategory, e.jumlah)
                txtHarga.text = itemView.context.getString(R.string.format_hasil_rupiah, e.harga.formatDecimalSeparator())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceHolder {
        val binding = InvoiceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InvoiceHolder(binding)
    }

    override fun getItemCount() = listInvoice.size

    override fun onBindViewHolder(holder: InvoiceHolder, position: Int) {
        holder.bind(listInvoice[position])
    }
}