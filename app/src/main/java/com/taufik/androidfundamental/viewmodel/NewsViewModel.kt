package com.taufik.androidfundamental.viewmodel

import androidx.lifecycle.ViewModel
import com.taufik.androidfundamental.data.news.NewsRepository

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    val getHeadlineNews = newsRepository.getHeadlineNews()
}