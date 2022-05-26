package com.taufik.androidfundamental.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taufik.androidfundamental.data.api.ApiConfig
import com.taufik.androidfundamental.data.response.CustomerReviewsItem
import com.taufik.androidfundamental.data.response.PostReviewResponse
import com.taufik.androidfundamental.data.response.Restaurant
import com.taufik.androidfundamental.data.response.RestaurantResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LiveDataApiViewModel : ViewModel() {

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview: LiveData<List<CustomerReviewsItem>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> = _snackBarText

    private val client = ApiConfig.getApiService()

    init {
        findRestaurant()
    }

    private fun findRestaurant() {
        _isLoading.value = true
        client.getRestaurant(RESTAURANT_ID)
            .enqueue(object : Callback<RestaurantResponse> {
                override fun onResponse(
                    call: Call<RestaurantResponse>,
                    response: Response<RestaurantResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _restaurant.value = responseBody.restaurant
                            _listReview.value = responseBody.restaurant.customerReviews
                        }
                    } else {
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun postReview(review: String) {
        _isLoading.value = true
        client.postReview(RESTAURANT_ID, "Dicoding", review)
            .enqueue(object : Callback<PostReviewResponse> {
                override fun onResponse(
                    call: Call<PostReviewResponse>,
                    response: Response<PostReviewResponse>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _listReview.value = responseBody.customerReviews
                        _snackBarText.value = responseBody.message
                    } else {
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    companion object {
        private const val TAG = "LiveDataApiViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }
}