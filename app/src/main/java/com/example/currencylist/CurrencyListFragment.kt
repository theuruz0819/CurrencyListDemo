package com.example.currencylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencylist.adapter.CurrencyAdapter
import com.example.currencylist.databinding.CurrencyListFragmentBinding
import com.example.currencylist.viewmodel.CurrencyViewModel
import com.example.currencylist.viewmodel.CurrencyViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CurrencyListFragment : Fragment() {
    private var binding: CurrencyListFragmentBinding? = null
    private lateinit var recyclerView: RecyclerView
    // only one job in this fragment, double click button will cancel previous job
    private var job : Job? = null

    private val viewModel: CurrencyViewModel by activityViewModels {
        CurrencyViewModelFactory(
            (activity?.application as CurrencyApplication).database.currencyDao()
        )
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = CurrencyListFragmentBinding.inflate(inflater, container, false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding!!.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val currencyAdapter = CurrencyAdapter {
            viewModel.onListItemClick(it)
        }
        recyclerView.adapter = currencyAdapter

        binding!!.buttonFirst.setOnClickListener{
            // cancel job if exist
            job?.cancel()
            job = lifecycle.coroutineScope.launch {
                viewModel.fullCurrency().collect() {
                    currencyAdapter.submitList(it)
                }
            }
        }

        binding!!.buttonSecond.setOnClickListener{
            // cancel job if exist
            job?.cancel()
            job = lifecycle.coroutineScope.launch {
                viewModel.currencySortByName().collect() {
                    currencyAdapter.submitList(it)
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}