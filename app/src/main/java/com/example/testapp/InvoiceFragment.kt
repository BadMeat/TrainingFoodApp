package com.example.testapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.databinding.FragmentInvoiceBinding
import com.example.testapp.util.formatDecimalSeparator
import com.example.testapp.view.InvoiceAdapter

class InvoiceFragment : Fragment() {

    private lateinit var binding: FragmentInvoiceBinding
    private lateinit var foodViewModel: FoodViewModel
    private var invoiceAdapter = InvoiceAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvoiceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodViewModel = ViewModelProvider(requireActivity())[FoodViewModel::class.java]

        // Ambil data (nama) yg dipassing dari BillFragment
        arguments?.let {
            binding.txtBayarPake.text = context?.resources?.getString(
                R.string.bayar_pake,
                InvoiceFragmentArgs.fromBundle(it).namaBayar
            )
        }

        binding.rcInvoice.apply {
            adapter = invoiceAdapter
            layoutManager = LinearLayoutManager(context)
        }

        foodViewModel.listPesanan.observe(requireActivity()) {
            invoiceAdapter.setInvoice(it)
        }

        // daftar harga dan belanja
        foodViewModel.totalBelanja.observe(requireActivity()) {
            binding.txtInvoiceTotal.text =
                context?.getString(R.string.format_total_rupiah, it.formatDecimalSeparator())
        }

        // Hitung total belanja
        foodViewModel.hitungTotalBelanja()

        // deeplink ke gojek
        binding.btnBayar.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("gojek://")
            startActivity(intent)
        }

    }
}