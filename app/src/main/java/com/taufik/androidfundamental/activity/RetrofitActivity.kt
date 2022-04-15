package com.taufik.androidfundamental.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.taufik.androidfundamental.adapter.ReviewAdapter
import com.taufik.androidfundamental.data.api.ApiConfig
import com.taufik.androidfundamental.data.response.CustomerReviewsItem
import com.taufik.androidfundamental.data.response.Restaurant
import com.taufik.androidfundamental.data.response.RestaurantResponse
import com.taufik.androidfundamental.databinding.ActivityRetrofitBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRetrofitBinding
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setRecyclerView()
        findRestaurant()
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

    private fun findRestaurant() {
        showLoading(true)
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object : Callback<RestaurantResponse> {
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setRestaurantData(responseBody.restaurant)
                        setReviewData(responseBody.restaurant.customerReviews)
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                showLoading(false)
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
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

    private fun showLoading(isShow: Boolean) = with(binding) {
        if (isShow) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    companion object {
        private val TAG = RetrofitActivity::class.java.simpleName
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }
}