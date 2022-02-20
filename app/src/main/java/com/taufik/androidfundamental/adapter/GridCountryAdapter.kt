package com.taufik.androidfundamental.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.data.Country
import com.taufik.androidfundamental.databinding.ItemGridCountriesBinding

class GridCountryAdapter(private val listCountry: ArrayList<Country>)
    : RecyclerView.Adapter<GridCountryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemGridCountriesBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listCountry[position])
    }

    override fun getItemCount(): Int = listCountry.size

    inner class MyViewHolder(private val binding: ItemGridCountriesBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(country.countryFlag)
                    .placeholder(R.color.purple_700)
                    .into(imgCountryFlag)

                tvCountryName.text = country.countryName
                tvCountryDescription.text = country.countryDescription

                gridCountries.setOnClickListener {
                    Toast.makeText(itemView.context, country.countryName, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}