package com.taufik.androidfundamental.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.taufik.androidfundamental.adapter.ReviewAdapter
import com.taufik.androidfundamental.data.response.CustomerReviewsItem
import com.taufik.androidfundamental.data.response.Restaurant
import com.taufik.androidfundamental.databinding.ActivityRetrofitBinding
import com.taufik.androidfundamental.viewmodel.LiveDataApiViewModel

class RetrofitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRetrofitBinding
    private lateinit var reviewAdapter: ReviewAdapter

    private val viewModel: LiveDataApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        initObserver()
        setRecyclerView()
        setAction()
    }

    private fun initObserver() {
        viewModel.restaurant.observe(this) { restaurant ->
            setRestaurantData(restaurant)
        }

        viewModel.listReview.observe(this) { consumerReviews ->
            setReviewData(consumerReviews)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.snackBarText.observe(this) {
            Snackbar.make(
                window.decorView.rootView,
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun setRecyclerView() {
        reviewAdapter = ReviewAdapter()
        binding.apply {
            with(rvReview) {
                val manager = LinearLayoutManager(this@RetrofitActivity)
                layoutManager = manager
                addItemDecoration(DividerItemDecoration(this@RetrofitActivity, manager.orientation))
                adapter = reviewAdapter
            }
        }
    }

    private fun setRestaurantData(restaurant: Restaurant) = with(binding){
        tvTitle.text = restaurant.name
        tvDescription.text = restaurant.description
        Glide.with(this@RetrofitActivity)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .into(imgPicture)
    }

    private fun setReviewData(consumerReviews: List<CustomerReviewsItem>) = with(binding){
        val listReview = ArrayList<String>()
        for (review in consumerReviews) {
            listReview.add(
                """
                ${review.review}
                - ${review.name}
                """.trimIndent()
            )
        }

        reviewAdapter.setReviews(listReview)
        etReview.setText("")
    }

    private fun setAction() = with(binding){
        btnSend.setOnClickListener {
            viewModel.postReview(etReview.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun showLoading(isShow: Boolean) = with(binding) {
        progressBar.visibility = if (isShow) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}