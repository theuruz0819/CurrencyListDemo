package com.example.currencylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencylist.database.Currency
import com.example.currencylist.databinding.CurrencyListItemBinding

class CurrencyAdapter(
    private val onItemClicked: (Currency) -> Unit
) : ListAdapter<Currency, CurrencyAdapter.CurrencyViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Currency>() {
            override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val viewHolder = CurrencyViewHolder(
            CurrencyListItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CurrencyViewHolder(
        private var binding:CurrencyListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: Currency) {
            binding.currencyNameTextView.text = currency.name
            binding.currencySymbolTextView.text = currency.symbol
        }
    }
}
