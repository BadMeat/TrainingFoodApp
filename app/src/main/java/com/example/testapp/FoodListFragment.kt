package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.databinding.FragmentFoodListBinding
import com.example.testapp.view.FoodListAdapter

class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding
    private lateinit var foodAdapter: FoodListAdapter
    private lateinit var foodViewModel: FoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodViewModel = ViewModelProvider(requireActivity())[FoodViewModel::class.java]
        foodAdapter = FoodListAdapter {
            foodViewModel.addPesanan(it)
            foodViewModel.removeList(it)
        }
        foodViewModel.loadData()

        binding.rcFood.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(context)
        }

        foodViewModel.listFood.observe(requireActivity()) {
            binding.rcFood.visibility = View.VISIBLE
            foodAdapter.setFoodList(it)
        }

        foodViewModel.isLoading.observe(requireActivity()) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            if (it) {
                binding.rcFood.visibility = View.GONE
            }
        }
    }
}