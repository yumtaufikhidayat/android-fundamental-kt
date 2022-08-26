package com.taufik.androidfundamental.viewmodel

import androidx.lifecycle.ViewModel
import com.taufik.androidfundamental.data.local.entity.NewsEntity
import com.taufik.androidfundamental.data.news.NewsRepository

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    val getHeadlineNews = newsRepository.getHeadlineNews()
    val getBookmarkedNews = newsRepository.getBookmarkedNews

    fun saveNews(newsEntity: NewsEntity) {
        newsRepository.setBookmarkedNews(newsEntity, true)
    }

    fun deleteNews(newsEntity: NewsEntity) {
        newsRepository.setBookmarkedNews(newsEntity, false)
    }
}