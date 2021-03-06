package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.androidfundamental.adapter.GridCountryAdapter
import com.taufik.androidfundamental.adapter.ListCountryAdapter
import com.taufik.androidfundamental.data.CountriesData
import com.taufik.androidfundamental.data.Country
import com.taufik.androidfundamental.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewBinding
    private lateinit var listCountryAdapter: ListCountryAdapter
    private lateinit var gridCountryAdapter: GridCountryAdapter
    private var listCountries: ArrayList<Country> = arrayListOf()
    private var title: String = "ASEAN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarTitle(title)
        showListCountries()
        setButtonOnClick()
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun showListCountries() {
        listCountries.addAll(CountriesData.listData)
        listCountryAdapter = ListCountryAdapter(listCountries)
        binding.apply {
            with(rvCountries) {
                layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
                adapter = listCountryAdapter
            }
        }
    }

    private fun showGridCountries() {
        listCountries.addAll(CountriesData.listData)
        gridCountryAdapter = GridCountryAdapter(listCountries)
        binding.apply {
            with(rvCountries) {
                layoutManager = GridLayoutManager(this@RecyclerViewActivity, 2)
                adapter = gridCountryAdapter
            }
        }
    }

    private fun setButtonOnClick() {
        binding.apply {
            btnLinearLayout.setOnClickListener {
                listCountries.clear()
                showListCountries()
            }

            btnGridLayout.setOnClickListener {
                listCountries.clear()
                showGridCountries()
            }
        }
    }
}