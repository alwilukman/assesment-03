package com.example.mykonter.ui.promo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.mykonter.databinding.FragmentPhoneBinding
import com.example.mykonter.network.ApiStatus2


class PromoFragment : Fragment() {


    private val viewModel: PromoViewModel by lazy {
        ViewModelProvider(this)[PromoViewModel::class.java]
    }

    private lateinit var binding: FragmentPhoneBinding
    private lateinit var myAdapter: PromoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPhoneBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        myAdapter = PromoAdapter()
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner, {
            myAdapter.updateData(it)
        })
        viewModel.getStatus().observe(viewLifecycleOwner, {
            updateProgress(it)
        })
        viewModel.scheduleUpdater(requireActivity().application)
    }

    private fun updateProgress(status: ApiStatus2) {
        when (status) {
            ApiStatus2.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus2.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus2.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }
}