package com.taufik.androidfundamental.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.taufik.androidfundamental.data.Country
import com.taufik.androidfundamental.databinding.ItemListCountriesBinding

class ListCountryAdapter(private val listCountry: ArrayList<Country>)
    : RecyclerView.Adapter<ListCountryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemListCountriesBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listCountry[position])
    }

    override fun getItemCount(): Int = listCountry.size

    inner class MyViewHolder(private val binding: ItemListCountriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(country.countryFlag)
                    .apply(RequestOptions().override(60, 60))
                    .into(imgCountryFlag)

                tvCountryName.text = country.countryName
                tvCountryDescription.text = country.countryDescription
            }
        }
    }
}