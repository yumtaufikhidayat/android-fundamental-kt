package com.taufik.androidfundamental.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taufik.androidfundamental.databinding.ItemListQuotesBinding

class ListQuoteAdapter: RecyclerView.Adapter<ListQuoteAdapter.ViewHolder>() {

    private val listQuotes = ArrayList<String>()

    fun showListQuotes(listQuotes: ArrayList<String>) {
        this.listQuotes.clear()
        this.listQuotes.addAll(listQuotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = ItemListQuotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(listQuotes[position])

    override fun getItemCount(): Int = listQuotes.size

    inner class ViewHolder(private val binding: ItemListQuotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quote: String) = with(binding){
            tvQuote.text = quote
        }
    }
}