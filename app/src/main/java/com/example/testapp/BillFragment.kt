package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.databinding.FragmentBillBinding
import com.example.testapp.model.BayarModel
import com.example.testapp.view.BayarListAdapter

class BillFragment : Fragment() {

    private lateinit var binding: FragmentBillBinding

    private var item: MutableList<BayarModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        binding.rcBayar.apply {
            adapter = BayarListAdapter(item)
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun initData() {
        val image = resources.obtainTypedArray(R.array.bayar_image)
        val nama = resources.getStringArray(R.array.bayar_nama)
        item.clear()
        (0 until image.length()).forEach {
            item.add(BayarModel(nama[it], image.getResourceId(it, 0)))
        }
        image.recycle()
    }
}