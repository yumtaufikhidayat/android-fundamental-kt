package com.taufik.androidfundamental.di

import android.content.Context
import com.taufik.androidfundamental.data.api.news.ApiConfig
import com.taufik.androidfundamental.data.local.room.NewsDatabase
import com.taufik.androidfundamental.data.news.NewsRepository
import com.taufik.androidfundamental.utils.news.AppExecutors

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }
}