package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.databinding.FragmentPaymentBinding
import com.example.testapp.util.formatDecimalSeparator
import com.example.testapp.view.PaymentListAdapter


class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var billAdapter: PaymentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodViewModel = ViewModelProvider(requireActivity())[FoodViewModel::class.java]
        foodViewModel.loadPesanan()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        billAdapter = PaymentListAdapter(pencetKurang = {
            if (it.jumlah > 1) {
                it.jumlah -= 1
                foodViewModel.updatePesanan(it)
            }
        }, pencetTambah = {
            it.jumlah += 1
            foodViewModel.updatePesanan(it)
        }, pencetHapus = {
            foodViewModel.hapusItem(it)
        })

        foodViewModel.hitungTotalBelanja()

        binding.rcBill.apply {
            adapter = billAdapter
            layoutManager = LinearLayoutManager(context)
        }

        foodViewModel.totalBelanja.observe(requireActivity()) {
            binding.txtTotal.text =
                context?.getString(R.string.format_total_rupiah, it.formatDecimalSeparator())
        }

        foodViewModel.listPesanan.observe(requireActivity()) {
            billAdapter.setFoodList(it)
        }

        binding.btnHapus.setOnClickListener {
            foodViewModel.hapusPesanan()
        }
    }
}